package com.metroai.metroai_backend.graph.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metroai.metroai_backend.graph.service.GraphService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/graph")
@RequiredArgsConstructor
public class GraphController {

    private final GraphService graphService;

    @GetMapping
    public Map<Long, List<Long>> graph() {

        return graphService.buildGraph();
    }

    @GetMapping("/path")
    public List<Long> path(

            @RequestParam Long source,

            @RequestParam Long destination

    ) {

        return graphService.findShortestPath(
                source,
                destination
        );
    }
}