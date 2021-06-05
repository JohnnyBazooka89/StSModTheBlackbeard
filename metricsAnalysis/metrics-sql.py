import json
import os
import psycopg2
import sys
import time
import traceback
from contextlib import redirect_stdout
from os import path

METRICS_PATH = 'D:\\metrics_runs\\minty_runs'
CHARACTER_CARD_PREFIX = ''
RELIC_PREFIX = ''

SKIP_ENDLESS_RUNS = True
AVERAGE_DAMAGE_TAKEN_COUNT_THRESHOLD = 5
CARD_CHOICES_CARDS_THRESHOLD = 5
WIN_RATIO_CARDS_THRESHOLD = 5
# WIN_RATIO_GROUP_UPGRADED_AND_NOT_UPGRADED = False #TODO: implement this in SQL version
WIN_RATIO_RELICS_THRESHOLD = 5
HOSTS_THRESHOLD = 5


def getNewEmptyWonAndLostDict():
    return {"won": 0, "lost": 0}


def getNewEmptyPickedNotPickedDict():
    return {"picked": 0, "not_picked": 0}


try:
    conn = psycopg2.connect(database='metrics', user='postgres', password='secret')
    cur = conn.cursor()

    for root, dirs, files in os.walk(METRICS_PATH):
        for file in files:
            absPath = path.join(root, file)
            if path.isfile(absPath):
                cur.execute("INSERT INTO run(file_path, status) VALUES (%s, %s) ON CONFLICT (file_path) DO NOTHING",
                            (absPath, 'NEW'))
                conn.commit()

    cur.execute("SELECT file_path FROM run WHERE status = 'NEW'")
    rows = cur.fetchall()
    for row in rows:
        absPath = row[0]
        try:
            runJson = json.loads(open(absPath, 'r', encoding='utf-8').read())
            if runJson["event"]["is_endless"] and SKIP_ENDLESS_RUNS:
                cur.execute("UPDATE run SET status = %s, is_endless = %s where file_path = %s",
                            ('SKIPPED', True, absPath))
                conn.commit()
                continue
            if runJson["event"]["floor_reached"] <= 1:
                cur.execute("UPDATE run SET status = %s, left_too_early = %s where file_path = %s",
                            ('SKIPPED', True, absPath))
                conn.commit()
                continue

            character = runJson["event"]["character_chosen"]
            asc = runJson["event"]["ascension_level"]
            host = runJson["host"] if "host" in runJson else "unknown"
            language = runJson["event"]["language"] if "language" in runJson["event"] else "unknown"
            victory = runJson["event"]["victory"]
            playTime = runJson["event"]["playtime"]

            cur.execute(
                "UPDATE run SET character = %s, ascension = %s, host = %s, language = %s, victory = %s, play_time = %s where file_path = %s",
                (character, asc, host, language, victory, playTime, absPath))
            conn.commit()

            for damageTakenEntry in runJson["event"]["damage_taken"]:
                if damageTakenEntry["damage"] >= 99999:
                    continue
                if not "enemies" in damageTakenEntry:
                    continue
                enemies = damageTakenEntry["enemies"]
                damage = damageTakenEntry["damage"]
                cur.execute("INSERT INTO damage_taken(run_file_path, enemies, damage) VALUES (%s, %s, %s)",
                            (absPath, enemies, damage))
            conn.commit()

            if "killed_by" in runJson["event"]:
                enemyKilling = runJson["event"]["killed_by"]
                cur.execute("INSERT INTO killed_by(run_file_path, enemy_id) VALUES (%s, %s)", (absPath, enemyKilling))
                conn.commit()

            for cardChoice in runJson["event"]["card_choices"]:
                cardPicked = cardChoice["picked"]
                cur.execute("INSERT INTO card_choice(run_file_path, card_id, picked) VALUES (%s, %s, %s)",
                            (absPath, cardPicked, True))
                for notPicked in cardChoice["not_picked"]:
                    cur.execute("INSERT INTO card_choice(run_file_path, card_id, picked) VALUES (%s, %s, %s)",
                                (absPath, notPicked, False))
            conn.commit()

            masterDeck = runJson["event"]["master_deck"]
            masterDeckGrouped = {}
            for card in masterDeck:
                cur.execute("INSERT INTO master_deck(run_file_path, card_id) VALUES (%s, %s)", (absPath, card))
            conn.commit()

            relics = runJson["event"]["relics"]
            for relicId in relics:
                cur.execute("INSERT INTO relic(run_file_path, relic_id) VALUES (%s, %s)", (absPath, relicId))
            conn.commit()

            cur.execute("UPDATE run SET status = %s WHERE file_path = %s", ('PROCESSED', absPath))
            conn.commit()

        except Exception as e:
            cur.execute("UPDATE run SET status = %s, error_message = %s WHERE file_path = %s",
                        ('ERROR', traceback.format_exc(), absPath))
            conn.commit()


    def timeString(timeInSeconds):
        return time.strftime('%H:%M:%S', time.gmtime(timeInSeconds))


    def winRatioString(won, lost):
        return str("??.??%" if (won + lost) == 0 else ("%.2f%%" % round(100 * won / (won + lost), 2)))


    def printWinRatio(wonRuns, lostRuns):
        print("Played: " + str(wonRuns + lostRuns) + ", W=" + str(wonRuns) + ", L=" + str(
            lostRuns) + ", R=" + winRatioString(wonRuns, lostRuns))


    def printAverageLength(averageLength):
        sum = averageLength["sum"]
        count = averageLength["count"]
        print("??:??:??" if count == 0 else "%s" % timeString(round(sum / count)))


    def printAverageDamageTaken(averageDamageTaken):
        for enemiesKey, enemiesValue in sorted(averageDamageTaken.items(),
                                               key=lambda e: -e[1]["sum"] / (e[1]["count"])):
            sum = enemiesValue["sum"]
            count = enemiesValue["count"]
            if count < AVERAGE_DAMAGE_TAKEN_COUNT_THRESHOLD:
                continue
            print(enemiesKey + ", Avg=%.2f" % (sum / count) + ", Sum=" + str(sum) + ", Count=" + str(count))
        print()


    def printKilledBy(killedBy):
        for key, value in sorted(killedBy.items(), key=lambda e: -e[1]):
            print(str(key) + " -> " + str(value))
        print()


    def printCardChoices(cardChoices):
        for key, value in sorted(cardChoices.items(),
                                 key=lambda e: -e[1]["picked"] / (e[1]["picked"] + e[1]["not_picked"])):
            if not key.startswith(CHARACTER_CARD_PREFIX):
                continue
            picked = value["picked"]
            notPicked = value["not_picked"]
            total = picked + notPicked
            if total < CARD_CHOICES_CARDS_THRESHOLD:
                continue
            print(key + ": P=" + str(picked) + ", NP=" + str(notPicked) + ", R=" + winRatioString(picked, notPicked))
        print()


    def printIsSpecificCardInDeckAndWinRatio(isSpecificCardInDeckAndWinRatio):
        for cardKey, cardValue in sorted(isSpecificCardInDeckAndWinRatio.items(),
                                         key=lambda e: -e[1]["won"] / (e[1]["won"] + e[1]["lost"])):
            if not cardKey.startswith(CHARACTER_CARD_PREFIX):
                continue
            won = cardValue["won"]
            lost = cardValue["lost"]
            if won + lost < WIN_RATIO_CARDS_THRESHOLD:
                continue
            print(cardKey + ", W=" + str(won) + ", L=" + str(lost) + ", R=" + winRatioString(won, lost))
        print()


    def printAmountOfSpecificCardsAndWinRatio(amountOfSpecificCardsAndWinRatio):
        for cardKey, cardValue in sorted(amountOfSpecificCardsAndWinRatio.items(), key=lambda e: e[0]):
            if not cardKey.startswith(CHARACTER_CARD_PREFIX):
                continue
            for amountKey, amountValue in sorted(cardValue.items(), key=lambda e: e[0]):
                won = amountValue["won"]
                lost = amountValue["lost"]
                if won + lost < WIN_RATIO_CARDS_THRESHOLD:
                    continue
                print(
                    cardKey + ", " + str(amountKey) + ", W=" + str(won) + ", L=" + str(lost) + ", R=" + winRatioString(
                        won, lost))
        print()


    def printHasSpecificRelicAndWinRatio(hasSpecificRelicAndWinRatio):
        for relicKey, relicValue in sorted(hasSpecificRelicAndWinRatio.items(),
                                           key=lambda e: -e[1]["won"] / (e[1]["won"] + e[1]["lost"])):
            if not relicKey.startswith(RELIC_PREFIX):
                continue
            won = relicValue["won"]
            lost = relicValue["lost"]
            if won + lost < WIN_RATIO_RELICS_THRESHOLD:
                continue
            print(relicKey + ", W=" + str(won) + ", L=" + str(lost) + ", R=" + winRatioString(won, lost))
        print()


    def printHosts(hosts):
        for key, value in sorted(hosts.items(), key=lambda e: -e[1]):
            if value < HOSTS_THRESHOLD:
                continue
            print(str(key) + " -> " + str(value))
        print()


    def printLanguage(hosts):
        for key, value in sorted(hosts.items(), key=lambda e: -e[1]):
            print(str(key) + " -> " + str(value))
        print()


    ascKeysInts = set()
    characterKeys = set()

    cur.execute("SELECT ascension FROM run WHERE status = 'PROCESSED'")
    rows = cur.fetchall()
    for row in rows:
        ascKeysInts.add(int(row[0]))

    cur.execute("SELECT character FROM run WHERE status = 'PROCESSED'")
    rows = cur.fetchall()
    for row in rows:
        characterKeys.add(row[0])

    onlyTheHighestAscension = (20,)

    cur.execute("SELECT count(*) FROM run")
    totalRuns = cur.fetchone()[0]

    cur.execute("SELECT count(*) FROM run WHERE status = 'SKIPPED' and is_endless = true")
    endlessRuns = cur.fetchone()[0]

    cur.execute("SELECT count(*) FROM run WHERE status = 'SKIPPED' and left_too_early = true")
    leftTooEarlyRuns = cur.fetchone()[0]

    cur.execute("SELECT count(*) FROM run WHERE status = 'ERROR'")
    exceptionRuns = cur.fetchone()[0]


    def emptyStringIfNone(string):
        return string if string else ''


    def getWinRatio(asc, character, victory):
        cur.execute("""SELECT count(*) FROM run 
        WHERE status = 'PROCESSED' and (ascension = %(asc)s or %(asc)s = '') and (character = %(character)s or %(character)s = '') AND victory = %(victory)s""",
                    {"asc": emptyStringIfNone(asc), "character": emptyStringIfNone(character), "victory": victory})
        return cur.fetchone()[0]


    def getAverageLength(asc, character, victory):
        cur.execute("""SELECT sum(play_time::int) FROM run 
        WHERE status = 'PROCESSED' and (ascension = %(asc)s or %(asc)s = '') and (character = %(character)s or %(character)s = '') AND victory = %(victory)s""",
                    {"asc": emptyStringIfNone(asc), "character": emptyStringIfNone(character), "victory": victory})
        sum = cur.fetchone()[0]
        cur.execute("""SELECT count(*) FROM run 
        WHERE status = 'PROCESSED' and (ascension = %(asc)s or %(asc)s = '') and (character = %(character)s or %(character)s = '') AND victory = %(victory)s""",
                    {"asc": emptyStringIfNone(asc), "character": emptyStringIfNone(character), "victory": victory})
        count = cur.fetchone()[0]
        return {"sum": sum, "count": count}


    def getCardChoices(asc, character):
        cur.execute("""SELECT card_id, picked, count(*) 
        FROM card_choice c 
        LEFT JOIN run r
        ON (c.run_file_path = r.file_path)
        WHERE status = 'PROCESSED' and (ascension = %(asc)s or %(asc)s = '') and (character = %(character)s or %(character)s = '')
        GROUP BY card_id, picked""",
                    {"asc": emptyStringIfNone(asc), "character": emptyStringIfNone(character)})
        rows = cur.fetchall()
        results = {}
        for row in rows:
            cardId = row[0]
            picked = row[1]
            if not cardId in results:
                results[cardId] = getNewEmptyPickedNotPickedDict()
            if picked:
                results[cardId]["picked"] = row[2]
            else:
                results[cardId]["not_picked"] = row[2]
        return results


    def getIsSpecificCardInDeckAndWinRatio(asc, character):
        cur.execute("""SELECT card_id, victory, count(distinct(run_file_path)) 
        FROM master_deck md 
        LEFT JOIN run r
        ON (md.run_file_path = r.file_path)
        WHERE status = 'PROCESSED' and (ascension = %(asc)s or %(asc)s = '') and (character = %(character)s or %(character)s = '')
        GROUP BY card_id, victory""",
                    {"asc": emptyStringIfNone(asc), "character": emptyStringIfNone(character)})
        rows = cur.fetchall()
        results = {}
        for row in rows:
            cardId = row[0]
            victory = row[1]
            if not cardId in results:
                results[cardId] = getNewEmptyWonAndLostDict()
            if victory:
                results[cardId]["won"] = row[2]
            else:
                results[cardId]["lost"] = row[2]
        return results


    def getAmountOfSpecificCardsAndWinRatio(asc, character):
        cur.execute("""SELECT card_id, amount, victory, count(*)
        FROM 
		(
            SELECT card_id, victory, count(*) as amount
		    FROM master_deck md 
            LEFT JOIN run r
            ON (md.run_file_path = r.file_path)
            WHERE status = 'PROCESSED' and (ascension = %(asc)s or %(asc)s = '') and (character = %(character)s or %(character)s = '')
		    GROUP BY card_id, file_path, victory
		) AS amd
        GROUP BY card_id, amount, victory""",
                    {"asc": emptyStringIfNone(asc), "character": emptyStringIfNone(character)})
        rows = cur.fetchall()
        results = {}
        for row in rows:
            cardId = row[0]
            amount = row[1]
            victory = row[2]
            if not cardId in results:
                results[cardId] = {}
            if not amount in results[cardId]:
                results[cardId][amount] = getNewEmptyWonAndLostDict()
            if victory:
                results[cardId][amount]["won"] = row[3]
            else:
                results[cardId][amount]["lost"] = row[3]
        return results


    def getHasSpecificRelicAndWinRatio(asc, character):
        cur.execute("""SELECT relic_id, victory, count(distinct(run_file_path)) 
        FROM relic re 
        LEFT JOIN run r
        ON (re.run_file_path = r.file_path)
        WHERE status = 'PROCESSED' and (ascension = %(asc)s or %(asc)s = '') and (character = %(character)s or %(character)s = '')
        GROUP BY relic_id, victory""",
                    {"asc": emptyStringIfNone(asc), "character": emptyStringIfNone(character)})
        rows = cur.fetchall()
        results = {}
        for row in rows:
            cardId = row[0]
            victory = row[1]
            if not cardId in results:
                results[cardId] = getNewEmptyWonAndLostDict()
            if victory:
                results[cardId]["won"] = row[2]
            else:
                results[cardId]["lost"] = row[2]
        return results


    def getAverageDamageTaken(asc, character):
        cur.execute("""SELECT enemies, sum(damage), count(*)
        FROM damage_taken dt
        LEFT JOIN run r
        ON (dt.run_file_path = r.file_path)
        WHERE status = 'PROCESSED' and (ascension = %(asc)s or %(asc)s = '') and (character = %(character)s or %(character)s = '')
        GROUP BY enemies""",
                    {"asc": emptyStringIfNone(asc), "character": emptyStringIfNone(character)})
        rows = cur.fetchall()
        results = {}
        for row in rows:
            results[row[0]] = {"sum": row[1], "count": row[2]}
        return results


    def getKilledBy(asc, character):
        cur.execute("""SELECT enemy_id, count(*)
        FROM killed_by kb
        LEFT JOIN run r
        ON (kb.run_file_path = r.file_path)
        WHERE status = 'PROCESSED' and (ascension = %(asc)s or %(asc)s = '') and (character = %(character)s or %(character)s = '')
        GROUP BY enemy_id""",
                    {"asc": emptyStringIfNone(asc), "character": emptyStringIfNone(character)})
        rows = cur.fetchall()
        results = {}
        for row in rows:
            results[row[0]] = row[1]
        return results


    def getHosts(asc, character):
        cur.execute("""SELECT host, count(*)
        FROM run r
        WHERE status = 'PROCESSED' and (ascension = %(asc)s or %(asc)s = '') and (character = %(character)s or %(character)s = '')
        GROUP BY host""",
                    {"asc": emptyStringIfNone(asc), "character": emptyStringIfNone(character)})
        rows = cur.fetchall()
        results = {}
        for row in rows:
            results[row[0]] = row[1]
        return results


    def getLanguage(asc, character):
        cur.execute("""SELECT language, count(*)
        FROM run r
        WHERE status = 'PROCESSED' and (ascension = %(asc)s or %(asc)s = '') and (character = %(character)s or %(character)s = '')
        GROUP BY language""",
                    {"asc": emptyStringIfNone(asc), "character": emptyStringIfNone(character)})
        rows = cur.fetchall()
        results = {}
        for row in rows:
            results[row[0]] = row[1]
        return results


    if not os.path.exists('report'):
        os.makedirs('report')

    with open('report/01_summary.txt', 'w') as f:
        with redirect_stdout(f):
            print(str(totalRuns) + " total runs")
            print(str(endlessRuns) + " endless runs were skipped")
            print(str(leftTooEarlyRuns) + " runs were skipped, because they were left too early")
            print(str(exceptionRuns) + " runs threw an exception")

    with open('report/02_win_ratio.txt', 'w') as f:
        with redirect_stdout(f):
            print("Win ratio on all ascensions:", end=' ')
            printWinRatio(getWinRatio(None, None, True), getWinRatio(None, None, False))
            print()
            for ascInt in sorted(ascKeysInts):
                asc = str(ascInt)
                print("Win ratio on ascension " + asc + ":", end=' ')
                printWinRatio(getWinRatio(asc, None, True), getWinRatio(asc, None, False))
            print()
            if len(characterKeys) > 1:
                for character in sorted(characterKeys):
                    won = getWinRatio(None, character, True)
                    lost = getWinRatio(None, character, False)
                    if won + lost > 0:
                        print("Win ratio on character " + character + " on all ascensions:", end=' ')
                        printWinRatio(won, lost)
                        print()
                    for ascInt in sorted(ascKeysInts):
                        asc = str(ascInt)
                        won = getWinRatio(asc, character, True)
                        lost = getWinRatio(asc, character, False)
                        if won + lost > 0:
                            print("Win ratio on character " + character + " on ascension " + str(asc) + ":", end=' ')
                            printWinRatio(won, lost)
                    print()

    with open('report/03_average_length.txt', 'w') as f:
        with redirect_stdout(f):
            print("Average length of won runs on all ascensions:", end=' ')
            printAverageLength(getAverageLength(None, None, True))
            print()
            for ascInt in sorted(ascKeysInts):
                asc = str(ascInt)
                length = getAverageLength(asc, None, True)
                if length['count'] > 0:
                    print("Average length of won runs on ascension " + str(asc) + ":", end=' ')
                    printAverageLength(length)
            print()

            print("Average length of lost runs on all ascensions:", end=' ')
            printAverageLength(getAverageLength(None, None, False))
            print()
            for ascInt in sorted(ascKeysInts):
                asc = str(ascInt)
                length = getAverageLength(asc, None, False)
                if length['count'] > 0:
                    print("Average length of lost runs on ascension " + str(asc) + ":", end=' ')
                    printAverageLength(length)
            print()

            if len(characterKeys) > 1:
                for character in sorted(characterKeys):
                    print("Average length of won runs " + character + " on all ascensions:", end=' ')
                    length = getAverageLength(None, character, True)
                    if length['count'] > 0:
                        printAverageLength(length)
                        print()
                    for ascInt in sorted(ascKeysInts):
                        asc = str(ascInt)
                        length = getAverageLength(asc, character, True)
                        if length['count'] > 0:
                            print("Average length of won runs " + character + " on ascension " + str(asc) + ":",
                                  end=' ')
                            printAverageLength(length)
                    print()
                    length = getAverageLength(None, character, False)
                    if length['count'] > 0:
                        print("Average length of lost runs " + character + " on all ascensions:", end=' ')
                        printAverageLength(getAverageLength(None, character, False))
                    print()
                    for ascInt in sorted(ascKeysInts):
                        asc = str(ascInt)
                        length = getAverageLength(asc, character, False)
                        if length['count'] > 0:
                            print("Average length of lost runs " + character + " on ascension " + str(asc) + ":",
                                  end=' ')
                            printAverageLength(length)
                    print()
            f.close()

    with open('report/04_card_choices.txt', 'w') as f:
        with redirect_stdout(f):
            print("P = Picked")
            print("NP = Not Picked")
            print("R = Ratio")
            print()
            print("Card choices on all ascensions:")
            printCardChoices(getCardChoices(None, None))
            if len(characterKeys) == 1:
                for ascInt in onlyTheHighestAscension:
                    asc = str(ascInt)
                    print("Card choices on ascension " + str(asc) + ":")
                    printCardChoices(getCardChoices(asc, None))
            if len(characterKeys) > 1:
                for character in sorted(characterKeys):
                    print("Card choices on character " + character + " on all ascensions:")
                    printCardChoices(getCardChoices(None, character))
                    for ascInt in onlyTheHighestAscension:
                        asc = str(ascInt)
                        print("Card choices on character " + character + " on ascension " + str(asc) + ":")
                        printCardChoices(getCardChoices(asc, character))

    with open('report/05_is_a_specific_card_in_deck_and_win_ratio.txt', 'w') as f:
        with redirect_stdout(f):
            if len(characterKeys) == 1:
                print("Is a specific card in deck and win ratio on all ascensions:")
                printIsSpecificCardInDeckAndWinRatio(getIsSpecificCardInDeckAndWinRatio(None, None))
                for ascInt in onlyTheHighestAscension:
                    asc = str(ascInt)
                    print("Is a specific card in deck and win ratio and win ratio on ascension " + str(asc) + ":")
                    printIsSpecificCardInDeckAndWinRatio(getIsSpecificCardInDeckAndWinRatio(asc, None))
            if len(characterKeys) > 1:
                for character in sorted(characterKeys):
                    print(
                        "Is a specific card in deck and win ratio and win ratio on character " + character + " on all ascensions:")
                    printIsSpecificCardInDeckAndWinRatio(getIsSpecificCardInDeckAndWinRatio(None, character))
                    for ascInt in onlyTheHighestAscension:
                        asc = str(ascInt)
                        print(
                            "Is a specific card in deck and win ratio and win ratio on character " + character + " on ascension " + str(
                                asc) + ":")
                        printIsSpecificCardInDeckAndWinRatio(getIsSpecificCardInDeckAndWinRatio(asc, character))

    with open('report/06_amount_of_specific_cards_and_win_ratio.txt', 'w') as f:
        with redirect_stdout(f):
            if len(characterKeys) == 1:
                print("Amount of specific cards and win ratio on all ascensions:")
                printAmountOfSpecificCardsAndWinRatio(getAmountOfSpecificCardsAndWinRatio(None, None))
                for ascInt in onlyTheHighestAscension:
                    asc = str(ascInt)
                    print("Amount of specific cards and win ratio on ascension " + str(asc) + ":")
                    printAmountOfSpecificCardsAndWinRatio(getAmountOfSpecificCardsAndWinRatio(asc, None))
            if len(characterKeys) > 1:
                for character in sorted(characterKeys):
                    print("Amount of specific cards and win ratio on character " + character + " on all ascensions:")
                    printAmountOfSpecificCardsAndWinRatio(getAmountOfSpecificCardsAndWinRatio(None, character))
                    for ascInt in onlyTheHighestAscension:
                        asc = str(ascInt)
                        print(
                            "Amount of specific cards and win ratio on character " + character + " on ascension " + str(
                                asc) + ":")
                        printAmountOfSpecificCardsAndWinRatio(getAmountOfSpecificCardsAndWinRatio(asc, character))

    with open('report/07_has_specific_relic_and_win_ratio.txt', 'w') as f:
        with redirect_stdout(f):
            print("Has a specific relic and win ratio on all ascensions:")
            printHasSpecificRelicAndWinRatio(getHasSpecificRelicAndWinRatio(None, None))
            for ascInt in onlyTheHighestAscension:
                asc = str(ascInt)
                print("Has a specific relic and win ratio on ascension " + str(asc) + ":")
                printHasSpecificRelicAndWinRatio(getHasSpecificRelicAndWinRatio(asc, None))
            if len(characterKeys) > 1:
                for character in sorted(characterKeys):
                    print(
                        "Has a specific relic and win ratio and win ratio on character " + character + " on all ascensions:")
                    printHasSpecificRelicAndWinRatio(getHasSpecificRelicAndWinRatio(None, character))
                    for ascInt in onlyTheHighestAscension:
                        asc = str(ascInt)
                        print(
                            "Has a specific relic and win ratio and win ratio on character " + character + " on ascension " + str(
                                asc) + ":")
                        printHasSpecificRelicAndWinRatio(getHasSpecificRelicAndWinRatio(asc, character))

    with open('report/08_average_damage_taken.txt', 'w') as f:
        with redirect_stdout(f):
            print("Average damage taken on all ascensions:")
            printAverageDamageTaken(getAverageDamageTaken(None, None))
            for ascInt in onlyTheHighestAscension:
                asc = str(ascInt)
                print("Average damage taken on ascension " + str(asc) + ":")
                printAverageDamageTaken(getAverageDamageTaken(asc, None))
            if len(characterKeys) > 1:
                for character in sorted(characterKeys):
                    print("Average damage taken on character " + character + " on all ascensions:")
                    printAverageDamageTaken(getAverageDamageTaken(None, character))
                    for ascInt in onlyTheHighestAscension:
                        asc = str(ascInt)
                        print("Average damage taken on character " + character + " on ascension " + str(asc) + ":")
                        printAverageDamageTaken(getAverageDamageTaken(asc, character))

    with open('report/09_killed_by.txt', 'w') as f:
        with redirect_stdout(f):
            print("Killed by on all ascensions:")
            printKilledBy(getKilledBy(None, None))
            for ascInt in onlyTheHighestAscension:
                asc = str(ascInt)
                print("Killed by on ascension " + str(asc) + ":")
                printKilledBy(getKilledBy(asc, None))
            if len(characterKeys) > 1:
                for character in sorted(characterKeys):
                    print("Killed by on character " + character + " on all ascensions:")
                    printKilledBy(getKilledBy(None, character))
                    for ascInt in onlyTheHighestAscension:
                        asc = str(ascInt)
                        print("Killed by on character " + character + " on ascension " + str(asc) + ":")
                        printKilledBy(getKilledBy(asc, character))

    with open('report/10_hosts.txt', 'w') as f:
        with redirect_stdout(f):
            print("Hosts on all ascensions:")
            printHosts(getHosts(None, None))
            for ascInt in onlyTheHighestAscension:
                asc = str(ascInt)
                print("Hosts on ascension " + str(asc) + ":")
                printHosts(getHosts(asc, None))
            if len(characterKeys) > 1:
                for character in sorted(characterKeys):
                    print("Hosts on character " + character + " on all ascensions:")
                    printHosts(getHosts(None, character))
                    for ascInt in onlyTheHighestAscension:
                        asc = str(ascInt)
                        print("Hosts on character " + character + " on ascension " + str(asc) + ":")
                        printHosts(getHosts(asc, character))

    with open('report/11_language.txt', 'w') as f:
        with redirect_stdout(f):
            print("Language on all ascensions:")
            printLanguage(getLanguage(None, None))
            for ascInt in onlyTheHighestAscension:
                asc = str(ascInt)
                print("Language on ascension " + str(asc) + ":")
                printLanguage(getLanguage(asc, None))
            if len(characterKeys) > 1:
                for character in sorted(characterKeys):
                    print("Language on character " + character + " on all ascensions:")
                    printLanguage(getLanguage(None, character))
                    for ascInt in onlyTheHighestAscension:
                        asc = str(ascInt)
                        print("Language on character " + character + " on ascension " + str(asc) + ":")
                        printLanguage(getLanguage(asc, character))

except Exception as e:
    print(traceback.format_exc())
    sys.exit(1)
finally:
    if conn:
        conn.close()
