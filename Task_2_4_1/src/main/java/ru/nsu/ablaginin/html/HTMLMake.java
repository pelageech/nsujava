package ru.nsu.ablaginin.html;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class HTMLMake implements HTMLBuilder{
    private final List<HTMLBuilder> builders = new ArrayList<>();

    public void addBuilder(HTMLBuilder builder) {
        builders.add(builder);
    }

    @Override
    public String build() {
        return "<html><body>"
              + builders.stream().map(HTMLBuilder::build).collect(Collectors.joining())
              + "</body></html>";
    }
}
