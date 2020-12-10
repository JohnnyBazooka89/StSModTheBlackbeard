import os
import json
from os import path
import time

METRICS_PATH = 'D:\\metrics_runs\\metrics\\2020'
CHARACTER_CARD_PREFIX = 'blackbeard:'
RELIC_PREFIX = ''

SHOW_WIN_RATIO = True
SHOW_AVERAGE_LENGTH = True
SHOW_CARD_CHOICES = True
SHOW_AVERAGE_DAMAGE_TAKEN = True
SHOW_KILLED_BY = True
SHOW_IS_SPECIFIC_CARD_IN_DECK_AND_WIN_RATIO = True
SHOW_AMOUNT_OF_SPECIFIC_CARDS_AND_WIN_RATIO = True
SHOW_HAS_SPECIFIC_RELIC_AND_WIN_RATIO = True
SHOW_HOSTS = True

SKIP_ENDLESS_RUNS = True
AVERAGE_DAMAGE_TAKEN_COUNT_THRESHOLD = 5
CARD_CHOICES_CARDS_THRESHOLD = 5
WIN_RATIO_CARDS_THRESHOLD = 5
WIN_RATIO_GROUP_UPGRADED_AND_NOT_UPGRADED = False
WIN_RATIO_RELICS_THRESHOLD = 10
HOSTS_THRESHOLD = 5

wonRuns = {}
lostRuns = {}
averageLengthWonRuns = {}
averageLengthLostRuns = {}
averageDamageTaken = {}
killedBy = {}
cardChoices = {}
isSpecificCardInDeckAndWinRatio = {}
amountOfSpecificCardsAndWinRatio = {}
hasSpecificRelicAndWinRatio = {}
hosts = {}

characterKeys = set()
ascKeys = set()

def initIfNeeded(map, key, defaultValue):
    if not key in map:
        map[key] = defaultValue

def getNewEmptyWonAndLostDict():
    return {"won": 0, "lost": 0}

def getNewEmptySumAndCountDict():
    return {"sum": 0, "count": 0}

for root, dirs, files in os.walk(METRICS_PATH):
    for file in files:
        absPath = path.join(root, file)
        if path.isfile(absPath):
            runJson = json.loads(open(absPath, 'r', encoding='utf-8').read())
            if runJson["event"]["is_endless"] and SKIP_ENDLESS_RUNS:
                continue
            character = runJson["event"]["character_chosen"]
            characterKeys.add(character)
            asc = runJson["event"]["ascension_level"]
            ascKeys.add(asc)
            host = runJson["host"] if "host" in runJson else "unknown"
            initIfNeeded(hosts, character, {})
            initIfNeeded(hosts[character], asc, {})
            initIfNeeded(hosts[character][asc], host, 0)
            hosts[character][asc][host] += 1
            victory = runJson["event"]["victory"]
            if victory:
                initIfNeeded(wonRuns, character, {})
                initIfNeeded(wonRuns[character], asc, 0)
                wonRuns[character][asc] += 1
                initIfNeeded(averageLengthWonRuns, character, {})
                initIfNeeded(averageLengthWonRuns[character], asc, getNewEmptySumAndCountDict())
                averageLengthWonRuns[character][asc]["sum"] += runJson["event"]["playtime"]
                averageLengthWonRuns[character][asc]["count"] += 1
            else:
                initIfNeeded(lostRuns, character, {})
                initIfNeeded(lostRuns[character], asc, 0)
                lostRuns[character][asc] += 1
                initIfNeeded(averageLengthLostRuns, character, {})
                initIfNeeded(averageLengthLostRuns[character], asc, getNewEmptySumAndCountDict())
                averageLengthLostRuns[character][asc]["sum"] += runJson["event"]["playtime"]
                averageLengthLostRuns[character][asc]["count"] += 1
            for damageTakenEntry in runJson["event"]["damage_taken"]:
                if damageTakenEntry["damage"] >= 99999:
                    continue;
                enemies = damageTakenEntry["enemies"]
                initIfNeeded(averageDamageTaken, character, {})
                initIfNeeded(averageDamageTaken[character], asc, {})
                initIfNeeded(averageDamageTaken[character][asc], enemies, getNewEmptySumAndCountDict())
                averageDamageTaken[character][asc][enemies]["sum"] += damageTakenEntry["damage"]
                averageDamageTaken[character][asc][enemies]["count"] += 1
            if "killed_by" in runJson["event"]:
                enemyKilling = runJson["event"]["killed_by"]
                initIfNeeded(killedBy, character, {})
                initIfNeeded(killedBy[character], asc, {})
                initIfNeeded(killedBy[character][asc], enemyKilling, 0)
                killedBy[character][asc][enemyKilling] += 1
            for cardChoice in runJson["event"]["card_choices"]:
                initIfNeeded(cardChoices, character, {})
                initIfNeeded(cardChoices[character], asc, {})
                initIfNeeded(cardChoices[character][asc], cardChoice["picked"], getNewEmptyWonAndLostDict())
                cardChoices[character][asc][cardChoice["picked"]]["won"] += 1
                for notPicked in cardChoice["not_picked"]:
                    initIfNeeded(cardChoices[character], asc, {})
                    initIfNeeded(cardChoices[character][asc], notPicked, getNewEmptyWonAndLostDict())
                    cardChoices[character][asc][notPicked]["lost"] += 1
            masterDeck = runJson["event"]["master_deck"]
            masterDeckGrouped = {}
            for card in masterDeck:
                initIfNeeded(masterDeckGrouped, card, 0)
                masterDeckGrouped[card] += 1
            for key, value in masterDeckGrouped.items():
                if WIN_RATIO_GROUP_UPGRADED_AND_NOT_UPGRADED:
                    index = key.rfind('+')
                    if index != -1:
                        key = key[:index]
                initIfNeeded(isSpecificCardInDeckAndWinRatio, character, {})
                initIfNeeded(isSpecificCardInDeckAndWinRatio[character], asc, {})
                initIfNeeded(isSpecificCardInDeckAndWinRatio[character][asc], key, getNewEmptyWonAndLostDict())
                initIfNeeded(amountOfSpecificCardsAndWinRatio, character, {})
                initIfNeeded(amountOfSpecificCardsAndWinRatio[character], asc, {})
                initIfNeeded(amountOfSpecificCardsAndWinRatio[character][asc], key, {})
                initIfNeeded(amountOfSpecificCardsAndWinRatio[character][asc][key], value, getNewEmptyWonAndLostDict())
                if victory:
                    isSpecificCardInDeckAndWinRatio[character][asc][key]["won"] += 1
                    amountOfSpecificCardsAndWinRatio[character][asc][key][value]["won"] += 1
                else:
                    isSpecificCardInDeckAndWinRatio[character][asc][key]["lost"] += 1
                    amountOfSpecificCardsAndWinRatio[character][asc][key][value]["lost"] += 1
            relics = runJson["event"]["relics"]
            for key in relics:
                initIfNeeded(hasSpecificRelicAndWinRatio, character, {})
                initIfNeeded(hasSpecificRelicAndWinRatio[character], asc, {})
                initIfNeeded(hasSpecificRelicAndWinRatio[character][asc], key, getNewEmptyWonAndLostDict())
                if victory:
                    hasSpecificRelicAndWinRatio[character][asc][key]["won"] += 1
                else:
                    hasSpecificRelicAndWinRatio[character][asc][key]["lost"] += 1

def timeString(timeInSeconds):
    return time.strftime('%H:%M:%S', time.gmtime(timeInSeconds))

def winRatioString(won, lost):
    return str("??.??%%" if (won+lost)==0 else ("%.2f%%" % round(100*won/(won+lost), 2)))

def printWinRatio(wonRuns, lostRuns):
    print("Played: " + str(wonRuns+lostRuns) + ", R=" + winRatioString(wonRuns,lostRuns) + ", W=" +str(wonRuns) + ", L=" + str(lostRuns))

def printAverageLength(averageLength):
    sum = averageLength["sum"]
    count = averageLength["count"]
    print("??:??:??" if count == 0 else "%s" % timeString(round(sum/count)))

def printAverageDamageTaken(averageDamageTaken):
    for enemiesKey, enemiesValue in sorted(averageDamageTaken.items(), key=lambda e: -e[1]["sum"]/(e[1]["count"])):
        sum = enemiesValue["sum"]
        count = enemiesValue["count"]
        if count < AVERAGE_DAMAGE_TAKEN_COUNT_THRESHOLD:
            continue
        print(enemiesKey + ", Avg=%.2f" % (sum/count) + ", Sum=" + str(sum)  + ", Count=" + str(count))
    print()

def printKilledBy(killedBy):
    for key, value in sorted(killedBy.items(), key=lambda e: -e[1]):
        print(str(key) + " -> " + str(value))
    print()
   
def printCardChoices(cardChoices):
    for key, value in sorted(cardChoices.items(), key=lambda e: -e[1]["won"] / (e[1]["won"]+e[1]["lost"])):
        if not key.startswith(CHARACTER_CARD_PREFIX):
            continue
        won = value["won"]
        lost = value["lost"]
        if won + lost < CARD_CHOICES_CARDS_THRESHOLD: 
            continue    
        print(key + ": " + str(won) + ", " + str(lost) + ", %.6f" % (won / (won+lost)))
    print()

def printIsSpecificCardInDeckAndWinRatio(isSpecificCardInDeckAndWinRatio):
    for cardKey, cardValue in sorted(isSpecificCardInDeckAndWinRatio.items(), key=lambda e: -e[1]["won"]/(e[1]["won"]+e[1]["lost"])):
        if not cardKey.startswith(CHARACTER_CARD_PREFIX):
            continue
        won = cardValue["won"]
        lost = cardValue["lost"]
        if won + lost < WIN_RATIO_CARDS_THRESHOLD: 
            continue
        print(cardKey + ", W=" + str(won) + ", L=" + str(lost)  + ", R=" + winRatioString(won, lost))
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
            print(cardKey + ", " + str(amountKey) + ", W=" + str(won) + ", L=" + str(lost)  + ", R=" + winRatioString(won, lost))
    print()

def printHasSpecificRelicAndWinRatio(hasSpecificRelicAndWinRatio):
    for relicKey, relicValue in sorted(hasSpecificRelicAndWinRatio.items(), key=lambda e: -e[1]["won"]/(e[1]["won"]+e[1]["lost"])):
        if not relicKey.startswith(RELIC_PREFIX):
            continue
        won = relicValue["won"]
        lost = relicValue["lost"]
        if won + lost < WIN_RATIO_RELICS_THRESHOLD: 
            continue
        print(relicKey + ", W=" + str(won) + ", L=" + str(lost)  + ", R=" + winRatioString(won, lost))
    print()

def printHosts(hosts):
    for key, value in sorted(hosts.items(), key=lambda e: -e[1]):
        if value < HOSTS_THRESHOLD:
            continue
        print(str(key) + " -> " + str(value))
    print()

wonRunsByCharacters = {}
lostRunsByCharacters = {}
averageLengthWonRunsByCharacters = {}
averageLengthLostRunsByCharacters = {}
averageDamageTakenByCharacters = {}
killedByByCharacters = {}
cardChoicesByCharacters = {}
isSpecificCardInDeckAndWinRatioByCharacters = {}
amountOfSpecificCardsAndWinRatioByCharacters = {}
hasSpecificRelicAndWinRatioByCharacters = {}
hostsByCharacters = {}

wonRunsByAscensions = {}
lostRunsByAscensions = {}
averageLengthWonRunsByAscensions = {}
averageLengthLostRunsByAscensions = {}
averageDamageTakenByAscensions = {}
killedByByAscensions = {}
cardChoicesByAscensions = {}
isSpecificCardInDeckAndWinRatioByAscensions = {}
amountOfSpecificCardsAndWinRatioByAscensions = {}
hasSpecificRelicAndWinRatioByAscensions = {}
hostsByAscensions = {}

wonRunsAll = 0
lostRunsAll = 0
averageLengthWonRunsAll = getNewEmptySumAndCountDict()
averageLengthLostRunsAll = getNewEmptySumAndCountDict()
averageDamageTakenAll = {}
killedByAll = {}
cardChoicesAll = {}
isSpecificCardInDeckAndWinRatioAll = {}
amountOfSpecificCardsAndWinRatioAll = {}
hasSpecificRelicAndWinRatioAll = {}
hostsAll = {}

for character in characterKeys:
    for asc in ascKeys:
        initIfNeeded(wonRunsByCharacters, character, 0)
        wonRunsByCharacters[character] += wonRuns.get(character,{}).get(asc, 0)
        initIfNeeded(wonRunsByAscensions, asc, 0)
        wonRunsByAscensions[asc] += wonRuns.get(character,{}).get(asc, 0)
        wonRunsAll += wonRuns.get(character,{}).get(asc, 0)
        initIfNeeded(lostRunsByCharacters, character, 0)
        lostRunsByCharacters[character] += lostRuns.get(character,{}).get(asc, 0)
        initIfNeeded(lostRunsByAscensions, asc, 0)
        lostRunsByAscensions[asc] += lostRuns.get(character,{}).get(asc, 0)
        lostRunsAll += lostRuns.get(character,{}).get(asc, 0)
        for key, value in averageDamageTaken.get(character,{}).get(asc, {}).items():
            initIfNeeded(averageDamageTakenByCharacters, character, {})
            initIfNeeded(averageDamageTakenByCharacters[character], key, getNewEmptySumAndCountDict())
            averageDamageTakenByCharacters[character][key]["sum"] += value["sum"]
            averageDamageTakenByCharacters[character][key]["count"] += value["count"]
            initIfNeeded(averageDamageTakenByAscensions, asc, {})
            initIfNeeded(averageDamageTakenByAscensions[asc], key, getNewEmptySumAndCountDict())
            averageDamageTakenByAscensions[asc][key]["sum"] += value["sum"]
            averageDamageTakenByAscensions[asc][key]["count"] += value["count"]
            initIfNeeded(averageDamageTakenAll, key, getNewEmptySumAndCountDict())
            averageDamageTakenAll[key]["sum"] += value["sum"]
            averageDamageTakenAll[key]["count"] += value["count"]
        averageLengthWonRun = averageLengthWonRuns.get(character,{}).get(asc, getNewEmptySumAndCountDict())
        initIfNeeded(averageLengthWonRunsByCharacters, character, getNewEmptySumAndCountDict())
        averageLengthWonRunsByCharacters[character]["sum"] += averageLengthWonRun["sum"]
        averageLengthWonRunsByCharacters[character]["count"] += averageLengthWonRun["count"]
        initIfNeeded(averageLengthWonRunsByAscensions, asc, getNewEmptySumAndCountDict())
        averageLengthWonRunsByAscensions[asc]["sum"] += averageLengthWonRun["sum"]
        averageLengthWonRunsByAscensions[asc]["count"] += averageLengthWonRun["count"]
        averageLengthWonRunsAll["sum"] += averageLengthWonRun["sum"]
        averageLengthWonRunsAll["count"] += averageLengthWonRun["count"]
        averageLengthLostRun = averageLengthLostRuns.get(character,{}).get(asc, getNewEmptySumAndCountDict())
        initIfNeeded(averageLengthLostRunsByCharacters, character, getNewEmptySumAndCountDict())
        averageLengthLostRunsByCharacters[character]["sum"] += averageLengthLostRun["sum"]
        averageLengthLostRunsByCharacters[character]["count"] += averageLengthLostRun["count"]
        initIfNeeded(averageLengthLostRunsByAscensions, asc, getNewEmptySumAndCountDict())
        averageLengthLostRunsByAscensions[asc]["sum"] += averageLengthLostRun["sum"]
        averageLengthLostRunsByAscensions[asc]["count"] += averageLengthLostRun["count"]
        averageLengthLostRunsAll["sum"] += averageLengthLostRun["sum"]
        averageLengthLostRunsAll["count"] += averageLengthLostRun["count"]
        for key, value in killedBy.get(character,{}).get(asc, {}).items():
            initIfNeeded(killedByByCharacters, character, {})
            initIfNeeded(killedByByCharacters[character], key, 0)
            killedByByCharacters[character][key] += value
            initIfNeeded(killedByByAscensions, asc, {})
            initIfNeeded(killedByByAscensions[asc], key, 0)
            killedByByAscensions[asc][key] += value
            initIfNeeded(killedByAll, key, 0)
            killedByAll[key] += value
        for key, value in cardChoices.get(character,{}).get(asc, {}).items():
            initIfNeeded(cardChoicesByCharacters, character, {})
            initIfNeeded(cardChoicesByCharacters[character], key, getNewEmptyWonAndLostDict())
            cardChoicesByCharacters[character][key]["won"] += value["won"]
            cardChoicesByCharacters[character][key]["lost"] += value["lost"]
            initIfNeeded(cardChoicesByAscensions, asc, {})
            initIfNeeded(cardChoicesByAscensions[asc], key, getNewEmptyWonAndLostDict())
            cardChoicesByAscensions[asc][key]["won"] += value["won"]
            cardChoicesByAscensions[asc][key]["lost"] += value["lost"]
            initIfNeeded(cardChoicesAll, key, getNewEmptyWonAndLostDict())
            cardChoicesAll[key]["won"] += value["won"]
            cardChoicesAll[key]["lost"] += value["lost"]
        for key, value in isSpecificCardInDeckAndWinRatio.get(character,{}).get(asc, {}).items():
            initIfNeeded(isSpecificCardInDeckAndWinRatioByCharacters, character, {})
            initIfNeeded(isSpecificCardInDeckAndWinRatioByCharacters[character], key, getNewEmptyWonAndLostDict())
            isSpecificCardInDeckAndWinRatioByCharacters[character][key]["won"] += value["won"]
            isSpecificCardInDeckAndWinRatioByCharacters[character][key]["lost"] += value["lost"]
            initIfNeeded(isSpecificCardInDeckAndWinRatioByAscensions, asc, {})
            initIfNeeded(isSpecificCardInDeckAndWinRatioByAscensions[asc], key, getNewEmptyWonAndLostDict())
            isSpecificCardInDeckAndWinRatioByAscensions[asc][key]["won"] += value["won"]
            isSpecificCardInDeckAndWinRatioByAscensions[asc][key]["lost"] += value["lost"]
            initIfNeeded(isSpecificCardInDeckAndWinRatioAll, key, getNewEmptyWonAndLostDict())
            isSpecificCardInDeckAndWinRatioAll[key]["won"] += value["won"]
            isSpecificCardInDeckAndWinRatioAll[key]["lost"] += value["lost"]
        for key, value in hasSpecificRelicAndWinRatio.get(character,{}).get(asc, {}).items():
            initIfNeeded(hasSpecificRelicAndWinRatioByCharacters, character, {})
            initIfNeeded(hasSpecificRelicAndWinRatioByCharacters[character], key, getNewEmptyWonAndLostDict())
            hasSpecificRelicAndWinRatioByCharacters[character][key]["won"] += value["won"]
            hasSpecificRelicAndWinRatioByCharacters[character][key]["lost"] += value["lost"]
            initIfNeeded(hasSpecificRelicAndWinRatioByAscensions, asc, {})
            initIfNeeded(hasSpecificRelicAndWinRatioByAscensions[asc], key, getNewEmptyWonAndLostDict())
            hasSpecificRelicAndWinRatioByAscensions[asc][key]["won"] += value["won"]
            hasSpecificRelicAndWinRatioByAscensions[asc][key]["lost"] += value["lost"]
            initIfNeeded(hasSpecificRelicAndWinRatioAll, key, getNewEmptyWonAndLostDict())
            hasSpecificRelicAndWinRatioAll[key]["won"] += value["won"]
            hasSpecificRelicAndWinRatioAll[key]["lost"] += value["lost"]
        for key, value in amountOfSpecificCardsAndWinRatio.get(character,{}).get(asc, {}).items():
            for amountKey, amountValue in value.items():
                initIfNeeded(amountOfSpecificCardsAndWinRatioByCharacters, character, {})
                initIfNeeded(amountOfSpecificCardsAndWinRatioByCharacters[character], key, {})
                initIfNeeded(amountOfSpecificCardsAndWinRatioByCharacters[character][key], amountKey, getNewEmptyWonAndLostDict())
                amountOfSpecificCardsAndWinRatioByCharacters[character][key][amountKey]["won"] += amountValue["won"]
                amountOfSpecificCardsAndWinRatioByCharacters[character][key][amountKey]["lost"] += amountValue["lost"]
                initIfNeeded(amountOfSpecificCardsAndWinRatioByAscensions, asc, {})
                initIfNeeded(amountOfSpecificCardsAndWinRatioByAscensions[asc], key, {})
                initIfNeeded(amountOfSpecificCardsAndWinRatioByAscensions[asc][key], amountKey, getNewEmptyWonAndLostDict())
                amountOfSpecificCardsAndWinRatioByAscensions[asc][key][amountKey]["won"] += amountValue["won"]
                amountOfSpecificCardsAndWinRatioByAscensions[asc][key][amountKey]["lost"] += amountValue["lost"]
                initIfNeeded(amountOfSpecificCardsAndWinRatioAll, key, {})
                initIfNeeded(amountOfSpecificCardsAndWinRatioAll[key], amountKey, getNewEmptyWonAndLostDict())
                amountOfSpecificCardsAndWinRatioAll[key][amountKey]["won"] += amountValue["won"]
                amountOfSpecificCardsAndWinRatioAll[key][amountKey]["lost"] += amountValue["lost"]
        for key, value in hosts.get(character,{}).get(asc, {}).items():
            initIfNeeded(hostsByCharacters, character, {})
            initIfNeeded(hostsByCharacters[character], key, 0)
            hostsByCharacters[character][key] += value
            initIfNeeded(hostsByAscensions, asc, {})
            initIfNeeded(hostsByAscensions[asc], key, 0)
            hostsByAscensions[asc][key] += value
            initIfNeeded(hostsAll, key, 0)
            hostsAll[key] += value

onlyTheHighestAscension = (20,)

if SHOW_WIN_RATIO:
    print("Win ratio on all ascensions:", end = ' ')
    printWinRatio(wonRunsAll, lostRunsAll)
    print()
    for asc in sorted(ascKeys):
        print("Win ratio on ascension " + str(asc) + ":", end = ' ')
        printWinRatio(wonRunsByAscensions.get(asc, {}), lostRunsByAscensions.get(asc, {}))
    print()
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Win ratio on character " + character + " on all ascensions:", end = ' ')
            printWinRatio(wonRunsByCharacters.get(character, {}), lostRunsByCharacters.get(character, {}))
            print()
            for asc in sorted(ascKeys):
                print("Win ratio on character " + character + " on ascension " + str(asc) + ":", end = ' ')
                printWinRatio(wonRuns.get(character, {}).get(asc, 0), lostRuns.get(character, {}).get(asc, 0))
            print()

if SHOW_AVERAGE_LENGTH:
    print("Average length of won runs on all ascensions:", end = ' ')
    printAverageLength(averageLengthWonRunsAll)
    print()
    for asc in sorted(ascKeys):
        print("Average length of won runs on ascension " + str(asc) + ":", end = ' ')
        printAverageLength(averageLengthWonRunsByAscensions.get(asc, {}))
    print()
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Average length of won runs " + character + " on all ascensions:", end = ' ')
            printAverageLength(averageLengthWonRunsByCharacters.get(character, {}))
            print()
            for asc in sorted(ascKeys):
                print("Average length of won runs " + character + " on ascension " + str(asc) + ":", end = ' ')
                printAverageLength(averageLengthWonRuns.get(character, {}).get(asc, getNewEmptySumAndCountDict()))
            print()
    print("Average length of lost runs on all ascensions:", end = ' ')
    printAverageLength(averageLengthLostRunsAll)
    print()
    for asc in sorted(ascKeys):
        print("Average length of lost runs on ascension " + str(asc) + ":", end = ' ')
        printAverageLength(averageLengthLostRunsByAscensions.get(asc, {}))
    print()
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Average length of lost runs " + character + " on all ascensions:", end = ' ')
            printAverageLength(averageLengthLostRunsByCharacters.get(character, {}))
            print()
            for asc in sorted(ascKeys):
                print("Average length of lost runs " + character + " on ascension " + str(asc) + ":", end = '')
                printAverageLength(averageLengthLostRuns.get(character, {}).get(asc, getNewEmptySumAndCountDict()))
            print()


if SHOW_CARD_CHOICES:
    print("Card choices on all ascensions:")
    printCardChoices(cardChoicesAll)
    for asc in onlyTheHighestAscension:
        print("Card choices on ascension " + str(asc) + ":")
        printCardChoices(cardChoicesByAscensions.get(asc,{}))
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Card choices on character " + character + " on all ascensions:")
            printCardChoices(cardChoicesByCharacters.get(character, {}))
            for asc in onlyTheHighestAscension:
                print("Card choices on character " + character + " on ascension " + str(asc) + ":")
                printCardChoices(cardChoices.get(character, {}).get(asc,{}))

if SHOW_AVERAGE_DAMAGE_TAKEN:
    print("Average damage taken on all ascensions:")
    printAverageDamageTaken(averageDamageTakenAll)
    for asc in onlyTheHighestAscension:
        print("Average damage taken on ascension " + str(asc) + ":")
        printAverageDamageTaken(averageDamageTakenByAscensions.get(asc,{}))
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Average damage taken on character " + character + " on all ascensions:")
            printAverageDamageTaken(averageDamageTakenByCharacters.get(character, {}))
            for asc in onlyTheHighestAscension:
                print("Average damage taken on character " + character + " on ascension " + str(asc) + ":")
                printAverageDamageTaken(averageDamageTaken.get(character, {}).get(asc,{}))

if SHOW_KILLED_BY:
    print("Killed by on all ascensions:")
    printKilledBy(killedByAll)
    for asc in onlyTheHighestAscension:
        print("Killed by on ascension " + str(asc) + ":")
        printKilledBy(killedByByAscensions.get(asc,{}))
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Killed by on character " + character + " on all ascensions:")
            printKilledBy(killedByByCharacters.get(character, {}))
            for asc in onlyTheHighestAscension:
                print("Killed by on character " + character + " on ascension " + str(asc) + ":")
                printKilledBy(killedBy.get(character, {}).get(asc,{}))

if SHOW_IS_SPECIFIC_CARD_IN_DECK_AND_WIN_RATIO:
    print("Is a specific card in deck and win ratio on all ascensions:")
    printIsSpecificCardInDeckAndWinRatio(isSpecificCardInDeckAndWinRatioAll)
    for asc in onlyTheHighestAscension:
        print("Is a specific card in deck and win ratio and win ratio on ascension " + str(asc) + ":")
        printIsSpecificCardInDeckAndWinRatio(isSpecificCardInDeckAndWinRatioByAscensions.get(asc,{}))
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Is a specific card in deck and win ratio and win ratio on character " + character + " on all ascensions:")
            printIsSpecificCardInDeckAndWinRatio(isSpecificCardInDeckAndWinRatioByCharacters.get(character, {}))
            for asc in onlyTheHighestAscension:
                print("Is a specific card in deck and win ratio and win ratio on character " + character + " on ascension " + str(asc) + ":")
                printIsSpecificCardInDeckAndWinRatio(isSpecificCardInDeckAndWinRatio.get(character, {}).get(asc,{}))

if SHOW_AMOUNT_OF_SPECIFIC_CARDS_AND_WIN_RATIO:
    print("Amount of specific cards and win ratio on all ascensions:")
    printAmountOfSpecificCardsAndWinRatio(amountOfSpecificCardsAndWinRatioAll)
    for asc in onlyTheHighestAscension:
        print("Amount of specific cards and win ratio on ascension " + str(asc) + ":")
        printAmountOfSpecificCardsAndWinRatio(amountOfSpecificCardsAndWinRatioByAscensions.get(asc,{}))
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Amount of specific cards and win ratio on character " + character + " on all ascensions:")
            printAmountOfSpecificCardsAndWinRatio(amountOfSpecificCardsAndWinRatioByCharacters.get(character, {}))
            for asc in onlyTheHighestAscension:
                print("Amount of specific cards and win ratio on character " + character + " on ascension " + str(asc) + ":")
                printAmountOfSpecificCardsAndWinRatio(amountOfSpecificCardsAndWinRatio.get(character, {}).get(asc,{}))

if SHOW_HAS_SPECIFIC_RELIC_AND_WIN_RATIO:
    print("Has a specific relic and win ratio on all ascensions:")
    printHasSpecificRelicAndWinRatio(hasSpecificRelicAndWinRatioAll)
    for asc in onlyTheHighestAscension:
        print("Has a specific relic and win ratio on ascension " + str(asc) + ":")
        printHasSpecificRelicAndWinRatio(hasSpecificRelicAndWinRatioByAscensions.get(asc,{}))
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Has a specific relic and win ratio and win ratio on character " + character + " on all ascensions:")
            printHasSpecificRelicAndWinRatio(hasSpecificRelicAndWinRatioByCharacters.get(character, {}))
            for asc in onlyTheHighestAscension:
                print("Has a specific relic and win ratio and win ratio on character " + character + " on ascension " + str(asc) + ":")
                printHasSpecificRelicAndWinRatio(hasSpecificRelicAndWinRatio.get(character, {}).get(asc,{}))


if SHOW_HOSTS:
    print("Hosts on all ascensions:")
    printHosts(hostsAll)
    for asc in onlyTheHighestAscension:
        print("Hosts on ascension " + str(asc) + ":")
        printHosts(hostsByAscensions.get(asc, {}))
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Hosts on character " + character + " on all ascensions:")
            printHosts(hostsByCharacters.get(character, {}))
            for asc in onlyTheHighestAscension:
                print("Hosts on character " + character + " on ascension " + str(asc) + ":")
                printHosts(hosts.get(character, {}).get(asc, {}))
