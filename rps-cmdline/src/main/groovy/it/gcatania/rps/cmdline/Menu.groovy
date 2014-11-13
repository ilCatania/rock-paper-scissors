/*
 * Copyright 2014 Gabriele Catania <gabriele.ctn@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.gcatania.rps.cmdline

import jline.console.ConsoleReader


/**
 * utility class to display multiple choice menus in the command line
 * @author ilCatania
 */
class Menu {

    private static class MenuItem {
        String commandId
        String label
    }

    private final String title

    private final List<MenuItem> items = []

    Menu(String title) {
        this.title = title
    }

    Menu addItem(String commandId, String label) {
        items.add(new MenuItem(commandId: commandId, label: label))
        return this
    }

    /**
     * show the menu content
     */
    void show() {
        println title
        println "=" * title.length()
        println()
        items.eachWithIndex { MenuItem item, int i ->
            println "${i+1} - ${item.label}"
        }
        println()
    }

    /**
     * show the menu content and ask for a choice from the user
     * @param console the console to read input from
     * @return the id of the selected command
     */
    String prompt(ConsoleReader console) {
        show()
        println 'Your choice: '
        while (true) {
            int choiceStr = console.readCharacter()
            try {
                int choice = Integer.parseInt(((char)choiceStr).toString())
                if(choice > 0 && choice <= items.size()) {
                    println()
                    return items[choice-1].commandId
                }
            } catch (NumberFormatException | NullPointerException e) {
                //ignore and wait for another character
            }
        }
    }
}
