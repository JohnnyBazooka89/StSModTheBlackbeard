# A util to check what keys are missing in localization files
# It may be not the best Python code ever, but it works :)

import json
import os

localizationParentPath = "src\\main\\resources\\blackbeard\\localization"
jsonFilenames = os.listdir(localizationParentPath + "\\eng")

languages = os.listdir(localizationParentPath)

for language in languages:

    if language == "eng":
        continue

    print("Language: {0}".format(language))

    for jsonFilename in jsonFilenames:

        print("File: {0}".format(jsonFilename))

        englishJsonFilepath = localizationParentPath + "\\eng\\" + jsonFilename
        otherLanguageJsonFilePath = localizationParentPath + "\\" + language + "\\" + jsonFilename

        englishJson = json.loads(open(englishJsonFilepath, "r", encoding="utf-8").read())
        otherLanguageJson = json.loads(open(otherLanguageJsonFilePath, "r", encoding="utf-8").read())

        for key in englishJson.keys():
            if key not in otherLanguageJson:
                print("Found key {0} without translation".format(key))

        for key in otherLanguageJson.keys():
            if key not in englishJson:
                print("Found key {0} that is not needed".format(key))
