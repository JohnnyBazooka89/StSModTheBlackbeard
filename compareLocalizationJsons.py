#A util to check what keys are missing in localization files
#It may be not the best Python code ever, but it works :)

import json

languages = ['pol', 'rus']
jsonFilenames = ['CardStrings.json', 'CharacterStrings.json', 'EventStrings.json', 'KeywordStrings.json', 'OrbStrings.json', 'PotionStrings.json', 'PowerStrings.json', 'RelicStrings.json'];
localizationParentPath = 'src\\main\\resources\\blackbeard\\localization'

for language in languages:

    print 'Language: {0}'.format(language)

    for jsonFilename in jsonFilenames:

        print 'File: {0}'.format(jsonFilename)

        englishJson = json.loads(open(localizationParentPath + '\\eng\\' + jsonFilename, 'r').read())
        otherLanguageJson = json.loads(open(localizationParentPath + "\\" + language + '\\' + jsonFilename, 'r').read())

        for key in englishJson.keys():
            if key not in otherLanguageJson:
                print 'Found key {0} without value'.format(key)

        for key in otherLanguageJson.keys():
            if key not in englishJson:
                print 'Found key {0} that is not needed'.format(key)