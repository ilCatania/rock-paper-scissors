/**
 *
 */
package it.gcatania.rps.cmdline

import jline.console.ConsoleReader


/**
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
