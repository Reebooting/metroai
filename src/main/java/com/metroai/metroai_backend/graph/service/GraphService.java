package com.metroai.metroai_backend.graph.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.metroai.metroai_backend.linestation.entity.LineStation;
import com.metroai.metroai_backend.linestation.repository.LineStationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GraphService {

private final LineStationRepository lineStationRepository;

public Map<Long, List<Long>> buildGraph() {

    Map<Long, List<Long>> graph = new HashMap<>();

    List<LineStation> lineStations = lineStationRepository.findAllByOrderByLineIdAscStationOrderAsc();

    for (int i = 0; i < lineStations.size() - 1; i++) {

        LineStation current = lineStations.get(i);

        LineStation next = lineStations.get(i + 1);

        if (!current.getLine().getId().equals(next.getLine().getId())) {
            continue;
        }

        Long currentStationId = current.getStation().getId();

        Long nextStationId = next.getStation().getId();

        graph.computeIfAbsent(currentStationId, k -> new ArrayList<>());

        graph.computeIfAbsent( nextStationId, k -> new ArrayList<>());

        graph.get(currentStationId).add(nextStationId);

        graph.get(nextStationId).add(currentStationId);
    }

    return graph;
}

public void printGraph() {

    Map<Long, List<Long>> graph = buildGraph();

    graph.forEach((stationId, neighbors) -> System.out.println(stationId + " -> " + neighbors));
}

public List<Long> findShortestPath( Long sourceStationId, Long destinationStationId
) {

    Map<Long, List<Long>> graph = buildGraph();

    Queue<Long> queue = new LinkedList<>();

    Set<Long> visited = new HashSet<>();

    Map<Long, Long> parent = new HashMap<>();

    queue.add(sourceStationId);
    visited.add(sourceStationId);

    while (!queue.isEmpty()) {

        Long current = queue.poll();

        if (current.equals(
                destinationStationId
        )) {
            break;
        }

        for (Long neighbor : graph.getOrDefault(current,List.of())) {

            if (!visited.contains(neighbor)) {

                visited.add(neighbor);

                parent.put(neighbor,current);

                queue.add(neighbor);
            }
        }
    }

    List<Long> path = new ArrayList<>();

    Long current = destinationStationId;

    while (current != null) {

        path.add(current);

        current = parent.get(current);
    }

    Collections.reverse(path);

    return path;
}

}