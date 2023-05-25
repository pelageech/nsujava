package ru.nsu.ablaginin.html;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class HtmlTable implements HtmlBuilder {
    @Getter
    final Th th = new Th();
    @Getter final List<Tr> tbody = new ArrayList<>();

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

    public String build() {
        return "<table><thead>"
              + th.build()
              + "</thead><tbody>"
              + tbody.stream().map(Tr::build).collect(Collectors.joining())
              + "</tbody></table>"
              + "<style>table, th, td { border: 1px solid black; }</style>";
    }

    @Data
    public static class Th implements HtmlBuilder {
        private final List<String> ths = new ArrayList<>();

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

    public static class Tr implements HtmlBuilder {
        private final List<String> tds = new ArrayList<>();

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
