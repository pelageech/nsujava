package ru.nsu.ablaginin.html;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class HtmlMake implements HtmlBuilder {
    private final List<HtmlBuilder> builders = new ArrayList<>();

    public void addBuilder(HtmlBuilder builder) {
        builders.add(builder);
    }

    @Override
    public String build() {
        return "<html><body>"
              + builders.stream().map(HtmlBuilder::build).collect(Collectors.joining())
              + "</body></html>";
    }
}
