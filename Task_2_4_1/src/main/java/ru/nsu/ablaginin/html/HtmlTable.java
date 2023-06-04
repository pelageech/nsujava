package ru.nsu.ablaginin.html;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A builder for table building.
 */
@NoArgsConstructor
public class HtmlTable implements HtmlBuilder {
    @Getter
    final Th th = new Th();
    @Getter final List<Tr> tbody = new ArrayList<>();

    /**
     * Checks the headers of tables and merges a given table
     * into table which method was called.
     *
     * @param table table that will be merged
     * @return true if merged
     */
    public boolean merge(HtmlTable table) {
        if (!this.th.equals(table.th)) {
            return false;
        }
        if (this.th.ths.size() == 0) {
            this.th.ths.addAll(table.th.ths);
        }

        tbody.addAll(table.tbody);
        return true;
    }

    @Override
    public String build() {
        return "<table><thead>"
              + th.build()
              + "</thead><tbody>"
              + tbody.stream().map(Tr::build).collect(Collectors.joining())
              + "</tbody></table>"
              + "<style>table, th, td { border: 1px solid black; }</style>";
    }

    /**
     * Implementation of TH tag.
     */
    @Data
    public static class Th implements HtmlBuilder {
        private final List<String> ths = new ArrayList<>();

        /**
         * Adds new TH tag.
         *
         * @param th th tag
         */
        public void addTh(String th) {
            ths.add(th);
        }
        @Override
        public String build() {
            return ths.stream()
                    .map(th -> "<th>" + th + "</th>")
                    .collect(
                            Collectors.joining("", "<tr>", "</tr>")
                    );
        }
    }

    /**
     * Implementation of TR tag.
     */
    public static class Tr implements HtmlBuilder {
        private final List<String> tds = new ArrayList<>();

        /**
         * Adds a TD tag to TR.
         *
         * @param td new td
         */
        public void addTd(String td) {
            tds.add(td);
        }

        @Override
        public String build() {
            return tds.stream()
                    .map(td -> "<td>" + td + "</td>")
                    .collect(
                            Collectors.joining("", "<tr>", "</tr>")
                    );
        }
    }
}
