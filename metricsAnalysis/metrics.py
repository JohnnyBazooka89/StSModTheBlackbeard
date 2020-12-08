import os
import json
from os import path

METRICS_PATH = 'D:\\metrics_runs\\metrics\\2020'
CHARACTER_CARD_PREFIX = 'blackbeard:'
RELIC_PREFIX = ''

SHOW_WIN_RATIO = True
SHOW_CARD_CHOICES = True
SHOW_KILLED_BY = True
SHOW_IS_SPECIFIC_CARD_IN_DECK_AND_WIN_RATIO = True
SHOW_AMOUNT_OF_SPECIFIC_CARDS_AND_WIN_RATIO = True
SHOW_HAS_SPECIFIC_RELIC_AND_WIN_RATIO = True
SHOW_HOSTS = True

SKIP_ENDLESS_RUNS = True
CARD_CHOICES_CARDS_THRESHOLD = 3
WIN_RATIO_CARDS_THRESHOLD = 3
WIN_RATIO_GROUP_UPGRADED_AND_NOT_UPGRADED = False
WIN_RATIO_RELICS_THRESHOLD = 10
HOSTS_THRESHOLD = 5

wonRuns = {}
lostRuns = {}
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
            for cardChoice in runJson["event"]["card_choices"]:
                initIfNeeded(cardChoices, character, {})
                initIfNeeded(cardChoices[character], asc, {})
                initIfNeeded(cardChoices[character][asc], cardChoice["picked"], [0,0])
                cardChoices[character][asc][cardChoice["picked"]][0] += 1
                for notPicked in cardChoice["not_picked"]:
                    initIfNeeded(cardChoices[character], asc, {})
                    initIfNeeded(cardChoices[character][asc], notPicked, [0,0])
                    cardChoices[character][asc][notPicked][1] += 1
            victory = runJson["event"]["victory"]
            masterDeck = runJson["event"]["master_deck"]
            masterDeckGrouped = {}
            for card in masterDeck:
                initIfNeeded(masterDeckGrouped, card, 0)
                masterDeckGrouped[card] += 1        
            for key, value in masterDeckGrouped.items():
                if key.endswith("+1") or key.find("+") == -1:
                    if WIN_RATIO_GROUP_UPGRADED_AND_NOT_UPGRADED:
                        key = key.replace("+1","")
                    initIfNeeded(isSpecificCardInDeckAndWinRatio, character, {})
                    initIfNeeded(isSpecificCardInDeckAndWinRatio[character], asc, {})
                    initIfNeeded(isSpecificCardInDeckAndWinRatio[character][asc], key, [0,0])
                    initIfNeeded(amountOfSpecificCardsAndWinRatio, character, {})
                    initIfNeeded(amountOfSpecificCardsAndWinRatio[character], asc, {})
                    initIfNeeded(amountOfSpecificCardsAndWinRatio[character][asc], key, {})
                    initIfNeeded(amountOfSpecificCardsAndWinRatio[character][asc][key], value, [0,0])
                    if victory:
                        isSpecificCardInDeckAndWinRatio[character][asc][key][0] += 1
                        amountOfSpecificCardsAndWinRatio[character][asc][key][value][0] += 1
                    else:
                        isSpecificCardInDeckAndWinRatio[character][asc][key][1] += 1
                        amountOfSpecificCardsAndWinRatio[character][asc][key][value][1] += 1
            relics = runJson["event"]["relics"]
            for key in relics:
                initIfNeeded(hasSpecificRelicAndWinRatio, character, {})
                initIfNeeded(hasSpecificRelicAndWinRatio[character], asc, {})
                initIfNeeded(hasSpecificRelicAndWinRatio[character][asc], key, [0,0])
                if victory:
                    hasSpecificRelicAndWinRatio[character][asc][key][0] += 1
                else:
                    hasSpecificRelicAndWinRatio[character][asc][key][1] += 1
            if victory:
                initIfNeeded(wonRuns, character, {})
                initIfNeeded(wonRuns[character], asc, 0)
                wonRuns[character][asc] += 1
            else:
                initIfNeeded(lostRuns, character, {})
                initIfNeeded(lostRuns[character], asc, 0)
                lostRuns[character][asc] += 1
            if "killed_by" in runJson["event"]:
                enemyKilling = runJson["event"]["killed_by"]
                initIfNeeded(killedBy, character, {})
                initIfNeeded(killedBy[character], asc, {})
                initIfNeeded(killedBy[character][asc], enemyKilling, 0)
                killedBy[character][asc][enemyKilling] += 1

def winRatioString(won, lost):
    return str("??.??%%" if (won+lost)==0 else ("%.2f%%" % round(100*won/(won+lost), 2)))

def printWinRatio(wonRuns, lostRuns):
    print("Played: " + str(wonRuns+lostRuns) + ", R=" + winRatioString(wonRuns,lostRuns) + ", W=" +str(wonRuns) + ", L=" + str(lostRuns))

def printKilledBy(killedBy):
    for key, value in sorted(killedBy.items(), key=lambda e: -e[1]):
        print(str(key) + " -> " + str(value))
    print()
   
def printCardChoices(cardChoices):
    for key, value in sorted(cardChoices.items(), key=lambda e: -e[1][0] / (e[1][0]+e[1][1])):
        if not key.startswith(CHARACTER_CARD_PREFIX):
            continue
        if value[0] + value[1] < CARD_CHOICES_CARDS_THRESHOLD: 
            continue    
        print(key + " " + str(value[0]) + " " + str(value[1]) + " " + str(value[0] / (value[0]+value[1])))
    print()

def printIsSpecificCardInDeckAndWinRatio(isSpecificCardInDeckAndWinRatio):
    for cardKey, cardValue in sorted(isSpecificCardInDeckAndWinRatio.items(), key=lambda e: -e[1][0]/(e[1][0]+e[1][1])):
        if cardKey.startswith(CHARACTER_CARD_PREFIX):
            won = cardValue[0]
            lost = cardValue[1]
            if won + lost < WIN_RATIO_CARDS_THRESHOLD: 
                continue
            print(cardKey + ", W=" + str(won) + ", L=" + str(lost)  + ", R=" + winRatioString(won, lost))
    print()

def printAmountOfSpecificCardsAndWinRatio(amountOfSpecificCardsAndWinRatio):
    for cardKey, cardValue in sorted(amountOfSpecificCardsAndWinRatio.items(), key=lambda e: e[0]):
        if not cardKey.startswith(CHARACTER_CARD_PREFIX):
            continue
        for amountKey, amountValue in sorted(cardValue.items(), key=lambda e: e[0]):
            won = amountValue[0]
            lost = amountValue[1]
            if won + lost < WIN_RATIO_CARDS_THRESHOLD: 
                continue
            print(cardKey + ", " + str(amountKey) + ", W=" + str(won) + ", L=" + str(lost)  + ", R=" + winRatioString(won, lost))
    print()

def printHasSpecificRelicAndWinRatio(hasSpecificRelicAndWinRatio):
    for relicKey, relicValue in sorted(hasSpecificRelicAndWinRatio.items(), key=lambda e: -e[1][0]/(e[1][0]+e[1][1])):
        if not relicKey.startswith(RELIC_PREFIX):
            continue
        won = relicValue[0]
        lost = relicValue[1]
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
killedByByCharacters = {}
cardChoicesByCharacters = {}
isSpecificCardInDeckAndWinRatioByCharacters = {}
amountOfSpecificCardsAndWinRatioByCharacters = {}
hasSpecificRelicAndWinRatioByCharacters = {}
hostsByCharacters = {}

wonRunsByAscensions = {}
lostRunsByAscensions = {}
killedByByAscensions = {}
cardChoicesByAscensions = {}
isSpecificCardInDeckAndWinRatioByAscensions = {}
amountOfSpecificCardsAndWinRatioByAscensions = {}
hasSpecificRelicAndWinRatioByAscensions = {}
hostsByAscensions = {}

wonRunsAll = 0
lostRunsAll = 0
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
        for key, value in cardChoices.get(character,{}).get(asc, {}).items():
            initIfNeeded(cardChoicesByCharacters, character, {})
            initIfNeeded(cardChoicesByCharacters[character], key, [0,0])
            cardChoicesByCharacters[character][key][0] += value[0]
            cardChoicesByCharacters[character][key][1] += value[1]
            initIfNeeded(cardChoicesByAscensions, asc, {})
            initIfNeeded(cardChoicesByAscensions[asc], key, [0,0])
            cardChoicesByAscensions[asc][key][0] += value[0]
            cardChoicesByAscensions[asc][key][1] += value[1]
            initIfNeeded(cardChoicesAll, key, [0,0])
            cardChoicesAll[key][0] += value[0]
            cardChoicesAll[key][1] += value[1]
        for key, value in isSpecificCardInDeckAndWinRatio.get(character,{}).get(asc, {}).items():
            initIfNeeded(isSpecificCardInDeckAndWinRatioByCharacters, character, {})
            initIfNeeded(isSpecificCardInDeckAndWinRatioByCharacters[character], key, [0,0])
            isSpecificCardInDeckAndWinRatioByCharacters[character][key][0] += value[0]
            isSpecificCardInDeckAndWinRatioByCharacters[character][key][1] += value[1]
            initIfNeeded(isSpecificCardInDeckAndWinRatioByAscensions, asc, {})
            initIfNeeded(isSpecificCardInDeckAndWinRatioByAscensions[asc], key, [0,0])
            isSpecificCardInDeckAndWinRatioByAscensions[asc][key][0] += value[0]
            isSpecificCardInDeckAndWinRatioByAscensions[asc][key][1] += value[1]
            initIfNeeded(isSpecificCardInDeckAndWinRatioAll, key, [0,0])
            isSpecificCardInDeckAndWinRatioAll[key][0] += value[0]
            isSpecificCardInDeckAndWinRatioAll[key][1] += value[1]
        for key, value in hasSpecificRelicAndWinRatio.get(character,{}).get(asc, {}).items():
            initIfNeeded(hasSpecificRelicAndWinRatioByCharacters, character, {})
            initIfNeeded(hasSpecificRelicAndWinRatioByCharacters[character], key, [0,0])
            hasSpecificRelicAndWinRatioByCharacters[character][key][0] += value[0]
            hasSpecificRelicAndWinRatioByCharacters[character][key][1] += value[1]
            initIfNeeded(hasSpecificRelicAndWinRatioByAscensions, asc, {})
            initIfNeeded(hasSpecificRelicAndWinRatioByAscensions[asc], key, [0,0])
            hasSpecificRelicAndWinRatioByAscensions[asc][key][0] += value[0]
            hasSpecificRelicAndWinRatioByAscensions[asc][key][1] += value[1]
            initIfNeeded(hasSpecificRelicAndWinRatioAll, key, [0,0])
            hasSpecificRelicAndWinRatioAll[key][0] += value[0]
            hasSpecificRelicAndWinRatioAll[key][1] += value[1]
        for key, value in amountOfSpecificCardsAndWinRatio.get(character,{}).get(asc, {}).items():
            for amountKey, amountValue in value.items():
                initIfNeeded(amountOfSpecificCardsAndWinRatioByCharacters, character, {})
                initIfNeeded(amountOfSpecificCardsAndWinRatioByCharacters[character], key, {})
                initIfNeeded(amountOfSpecificCardsAndWinRatioByCharacters[character][key], amountKey, [0,0])
                amountOfSpecificCardsAndWinRatioByCharacters[character][key][amountKey][0] += amountValue[0]
                amountOfSpecificCardsAndWinRatioByCharacters[character][key][amountKey][1] += amountValue[1]
                initIfNeeded(amountOfSpecificCardsAndWinRatioByAscensions, asc, {})
                initIfNeeded(amountOfSpecificCardsAndWinRatioByAscensions[asc], key, {})
                initIfNeeded(amountOfSpecificCardsAndWinRatioByAscensions[asc][key], amountKey, [0,0])
                amountOfSpecificCardsAndWinRatioByAscensions[asc][key][amountKey][0] += amountValue[0]
                amountOfSpecificCardsAndWinRatioByAscensions[asc][key][amountKey][1] += amountValue[1]
                initIfNeeded(amountOfSpecificCardsAndWinRatioAll, key, {})
                initIfNeeded(amountOfSpecificCardsAndWinRatioAll[key], amountKey, [0,0])
                amountOfSpecificCardsAndWinRatioAll[key][amountKey][0] += amountValue[0]
                amountOfSpecificCardsAndWinRatioAll[key][amountKey][1] += amountValue[1]
        for key, value in hosts.get(character,{}).get(asc, {}).items():
            initIfNeeded(hostsByCharacters, character, {})
            initIfNeeded(hostsByCharacters[character], key, 0)
            hostsByCharacters[character][key] += value
            initIfNeeded(hostsByAscensions, asc, {})
            initIfNeeded(hostsByAscensions[asc], key, 0)
            hostsByAscensions[asc][key] += value
            initIfNeeded(hostsAll, key, 0)
            hostsAll[key] += value
        for key, value in killedBy.get(character,{}).get(asc, {}).items():
            initIfNeeded(killedByByCharacters, character, {})
            initIfNeeded(killedByByCharacters[character], key, 0)
            killedByByCharacters[character][key] += value
            initIfNeeded(killedByByAscensions, asc, {})
            initIfNeeded(killedByByAscensions[asc], key, 0)
            killedByByAscensions[asc][key] += value
            initIfNeeded(killedByAll, key, 0)
            killedByAll[key] += value

onlyTheHighestAscension = (20,)

if SHOW_WIN_RATIO:
    print("Win ratio on all characters on all ascensions:", end = ' ')
    printWinRatio(wonRunsAll, lostRunsAll)
    print()
    for asc in sorted(ascKeys):
        print("Win ratio on all characters on ascension " + str(asc) + ":", end = ' ')
        printWinRatio(wonRunsByAscensions.get(asc, {}), lostRunsByAscensions.get(asc, {}))
    print()
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Win ratio on character " + character + " on all ascensions:", end = ' ')
            printWinRatio(wonRunsByCharacters.get(character, {}), lostRunsByCharacters.get(character, {}))
            print()
            for asc in sorted(ascKeys):
                print("Win ratio on character " + character + " on ascension " + str(asc) + ":", end = '')
                printWinRatio(wonRuns.get(character, {}).get(asc, 0), lostRuns.get(character, {}).get(asc, 0))
            print()

if SHOW_CARD_CHOICES:
    print("Card choices on all characters on all ascensions:")
    printCardChoices(cardChoicesAll)
    for asc in onlyTheHighestAscension:
        print("Card choices on all characters on ascension " + str(asc) + ":")
        printCardChoices(cardChoicesByAscensions.get(asc,{}))
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Card choices on character " + character + " on all ascensions:")
            printCardChoices(cardChoicesByCharacters.get(character, {}))
            for asc in onlyTheHighestAscension:
                print("Card choices on character " + character + " on ascension " + str(asc) + ":")
                printCardChoices(cardChoices.get(character, {}).get(asc,{}))

if SHOW_KILLED_BY:
    print("Killed by on all characters on all ascensions:")
    printKilledBy(killedByAll)
    for asc in onlyTheHighestAscension:
        print("Killed by on all characters on ascension " + str(asc) + ":")
        printKilledBy(killedByByAscensions.get(asc,{}))
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Killed by on character " + character + " on all ascensions:")
            printKilledBy(killedByByCharacters.get(character, {}))
            for asc in onlyTheHighestAscension:
                print("Killed by on character " + character + " on ascension " + str(asc) + ":")
                printKilledBy(killedBy.get(character, {}).get(asc,{}))

if SHOW_IS_SPECIFIC_CARD_IN_DECK_AND_WIN_RATIO:
    print("Is a specific card in deck and win ratio on all characters on all ascensions:")
    printIsSpecificCardInDeckAndWinRatio(isSpecificCardInDeckAndWinRatioAll)
    for asc in onlyTheHighestAscension:
        print("Is a specific card in deck and win ratio and win ratio on all characters on ascension " + str(asc) + ":")
        printIsSpecificCardInDeckAndWinRatio(isSpecificCardInDeckAndWinRatioByAscensions.get(asc,{}))
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Is a specific card in deck and win ratio and win ratio on character " + character + " on all ascensions:")
            printIsSpecificCardInDeckAndWinRatio(isSpecificCardInDeckAndWinRatioByCharacters.get(character, {}))
            for asc in onlyTheHighestAscension:
                print("Is a specific card in deck and win ratio and win ratio on character " + character + " on ascension " + str(asc) + ":")
                printIsSpecificCardInDeckAndWinRatio(isSpecificCardInDeckAndWinRatio.get(character, {}).get(asc,{}))

if SHOW_AMOUNT_OF_SPECIFIC_CARDS_AND_WIN_RATIO:
    print("Amount of specific cards and win ratio on all characters on all ascensions:")
    printAmountOfSpecificCardsAndWinRatio(amountOfSpecificCardsAndWinRatioAll)
    for asc in onlyTheHighestAscension:
        print("Amount of specific cards and win ratio on all characters on ascension " + str(asc) + ":")
        printAmountOfSpecificCardsAndWinRatio(amountOfSpecificCardsAndWinRatioByAscensions.get(asc,{}))
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Amount of specific cards and win ratio on character " + character + " on all ascensions:")
            printAmountOfSpecificCardsAndWinRatio(amountOfSpecificCardsAndWinRatioByCharacters.get(character, {}))
            for asc in onlyTheHighestAscension:
                print("Amount of specific cards and win ratio on character " + character + " on ascension " + str(asc) + ":")
                printAmountOfSpecificCardsAndWinRatio(amountOfSpecificCardsAndWinRatio.get(character, {}).get(asc,{}))

if SHOW_HAS_SPECIFIC_RELIC_AND_WIN_RATIO:
    print("Has a specific relic and win ratio on all characters on all ascensions:")
    printHasSpecificRelicAndWinRatio(hasSpecificRelicAndWinRatioAll)
    for asc in onlyTheHighestAscension:
        print("Has a specific relic and win ratio on all characters on ascension " + str(asc) + ":")
        printHasSpecificRelicAndWinRatio(hasSpecificRelicAndWinRatioByAscensions.get(asc,{}))
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Has a specific relic and win ratio and win ratio on character " + character + " on all ascensions:")
            printHasSpecificRelicAndWinRatio(hasSpecificRelicAndWinRatioByCharacters.get(character, {}))
            for asc in onlyTheHighestAscension:
                print("Has a specific relic and win ratio and win ratio on character " + character + " on ascension " + str(asc) + ":")
                printHasSpecificRelicAndWinRatio(hasSpecificRelicAndWinRatio.get(character, {}).get(asc,{}))


if SHOW_HOSTS:
    print("Hosts on all characters on all ascensions:")
    printHosts(hostsAll)
    for asc in onlyTheHighestAscension:
        print("Hosts on all characters on ascension " + str(asc) + ":")
        printHosts(hostsByAscensions.get(asc, {}))
    if len(characterKeys) > 1:
        for character in sorted(characterKeys):
            print("Hosts on character " + character + " on all ascensions:")
            printHosts(hostsByCharacters.get(character, {}))
            for asc in onlyTheHighestAscension:
                print("Hosts on character " + character + " on ascension " + str(asc) + ":")
                printHosts(hosts.get(character, {}).get(asc, {}))
