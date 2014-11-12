/**
 *
 */
package it.gcatania.rps.cmdline

/**
 * @author ilCatania
 *
 */
final class Utils {

    static void table(def rows, boolean header) {
        def rowStrings = rows.collect({it.collect({String.valueOf(it)})})
        def columnWidths = rowStrings.transpose()*.collect({it.length()})*.max()

        rowStrings.eachWithIndex { cells, i ->
            StringBuilder rowOutput = new StringBuilder().append('|')
            cells.eachWithIndex { String cell, j ->
                rowOutput.append(' ').append(cell.padRight(columnWidths[j])).append(' |')
            }
            println rowOutput.toString()
            if(i == 0 && header) {
                println( '=' * (columnWidths.sum() + 3 * columnWidths.size() + 1))
            }
        }
    }
}
