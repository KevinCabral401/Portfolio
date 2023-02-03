#pragma once
/**
	CS280 graphs
	@file bst.h
	@author Kevin Cabral
*/
#ifndef GRAPH_H_
#define GRAPH_H_
#include <iostream>
using namespace std;
#include <vector>
#include <queue> // for use with BFS
#include <stack> // for use with DFS
#include <climits> // for INT_MAX

// must use these structs
// they represent the vertices in our graph
struct adjCity;
struct city;
struct adjCity
{
	city* v;
	int weight;
};
struct city
{
	string key; // stores the name of the city
	vector<adjCity*> adjacent;
	bool visited; // for traversals (DFS/BFS)
	bool solved; // for Dijsktra's
	int distance; // for keeping track of distance in Dijkstra's
	city* parent; // for keeping track of parent in Dijkstra's
};

class graph {
private:
	vector<city*> vertices;
public:
	graph(); //constructor
	~graph(); //destructor
	void insertCity(string cityName); // inserts a new vertex (city)
	void insertEdge(string firstCity, string secondCity, int weight); // inserts edge with specified weight
	void printGraph(); // prints each vertex and it's adj vertices
	city* search(string cityName); // finds and returns city 
	void bft(string startCity); // prints out the vertices in a BF traversal from the starting city
	void dft(string startCity); // prints out the vertices in a DF traversal from the starting city
	city* dijkstras(string start, string end); // Dijkstra's algorithm!
	void printShortestPath(city* endV); // to be called after Dijkstra's
};


// Constructor
graph::graph() {
	cout << "Constructing." << endl;
}

// Destructor
// need to actually call delete on each struct we made with new
// Should loop through, for each city, delete all adjacent cities, then delete the city
graph::~graph()
{
	cout << "Destructing." << endl;
	// TODO: implement
}

/*
* Method name: insertCity
* Purpose: accepts a city name, adds a new vertex
* @param cityName - the name of the city
* @return - none
* Notes - should only add if there isn't already a vertex with that key,
* if there is a vertex with that key, print out an "error message"
*/
void graph::insertCity(string cityName)
{
	cout << "Inserting " << cityName << endl;

	bool found = false;
	for (int i = 0; i < vertices.size(); i++) {
		if (vertices[i]->key == cityName) {
			found = true;
			cout << endl << "Error: Key allready Exists" << endl << endl;
			break;
		}
	}

	if (found == false) {
		city* newCity = new city;
		newCity->key = cityName;
		newCity->visited = false;
		vertices.push_back(newCity);
	}
}

/*
* Method name: insertEdge
* Purpose: adds an edge between two cities
* @param firstCity - the first city in the edge
* @param secondCity - the second city in the edge
* @param weight - the weight of the edge (useful later for searching)
* @return - none
* Notes - should, technically, add two edges. The edge: firstCity -> secondCity
* and the edge: secondCity -> firstCity.
* You can follow most of the pseudocode in the book, but will need to add some additional
* functionality to add both edges.
*/
void graph::insertEdge(string firstCity, string secondCity, int weight)
{
	cout << "Inserting edges between " << firstCity << " and " << secondCity << " with weight " << weight << endl;
	
	for (int x = 0; x < vertices.size(); x++) {
		if (vertices[x]->key == firstCity) {
			for (int y = 0; y < vertices.size(); y++) {
				if (vertices[y]->key == secondCity && x != y) {
					adjCity* adj = new adjCity;
					adj->v = vertices[y];
					adj->weight = weight;
					vertices[x]->adjacent.push_back(adj);
				}
			}
		}

		if (vertices[x]->key == secondCity) {
			for (int y = 0; y < vertices.size(); y++) {
				if (vertices[y]->key == firstCity && x != y) {
					adjCity* adj = new adjCity;
					adj->v = vertices[y];
					adj->weight = weight;
					vertices[x]->adjacent.push_back(adj);
				}
			}
		}
	}
}

void graph::printGraph()
{
	for (int i = 0; i < vertices.size(); i++)
	{
		cout << vertices[i]->key << "-->";
		for (int j = 0; j < vertices[i]->adjacent.size(); j++)
		{
			cout << vertices[i]->adjacent[j]->v->key << "(" << vertices[i]->adjacent[j]->weight << ") ";
		}
		cout << endl;
	}
}

/*
* Method name: search
* Purpose: finds a key and returns the associated city pointer
* @param cityName - the key we are searching for
* @return - the pointer to that city, return NULL if the city we want is not in the graph
*/
city* graph::search(string cityName)
{
	cout << "Searching for " << cityName << endl;
	for (int x = 0; x < vertices.size(); x++) {
		if (vertices[x]->key == cityName) {
			return vertices[x];
		}
	}
	return NULL;
}

/*
* Method name: bft (breadth first traversal)
* Purpose: prints out a breadth first traversal from a start vertex
* @param startCity - the city we are starting from
* @return - none
* Notes - just print each city key as you traverse it, must use a queue!
* At the end, make sure to loop back through vertices, setting visited to false for each!
*/
void graph::bft(string startCity)
{
	queue<city*> q;

	cout << "Running BFT starting at " << startCity << endl;
	city* start = search(startCity);
	start->visited = true;
	q.push(start);
	while (!q.empty()) {
		city* temp = q.front();
		q.pop();
		for (int x = 0; x < temp->adjacent.size(); x++) {
			if (temp->adjacent[x]->v->visited == false) {
				temp->adjacent[x]->v->visited = true;
				cout << temp->adjacent[x]->v->key << endl;
				q.push(temp->adjacent[x]->v);
			}
		}
	}
	for (int x = 0; x < vertices.size(); x++) {
		vertices[x]->visited = false;
	}
}

/*
* Method name: dft (depth first traversal)
* Purpose: prints out a depth first traversal from a start vertex
* @param startCity - the city we are starting from
* @return - none
* Notes - just print each city key as you traverse it, must use a stack!
* At the end, make sure to loop back through vertices, setting visited to false for each!
*/
void graph::dft(string startCity)
{
	stack<city*> s;

	cout << "Running DFT starting at " << startCity << endl;
	city* start = search(startCity);
	start->visited = true;
	start->distance = 0;
	s.push(start);
	while (!s.empty()) {
		city* ve = s.top();
		cout << ve->key << endl;
		s.pop();
		for (int x = 0; x < ve->adjacent.size(); x++) {
			if (!ve->adjacent[x]->v->visited) {
				ve->adjacent[x]->v->visited = true;
				s.push(ve->adjacent[x]->v);
			}
		}
	}
	for (int x = 0; x < vertices.size(); x++) {
		vertices[x]->visited = false;
	}
}

/*
* Method name: printShortestPath
* Purpose: starts at end vertex, walks backwards to the start
* 	vertex, prints the path in the correct order from start to finish
* @param endV - the city that we found the shortest path to
* @return - none
* Notes - use the parent pointers, may need a data structure to store
* 	the path in the right order
*/
void graph::printShortestPath(city* endV)
{

	queue<city*> q;
	city* end = endV;
	end->visited = true;

	q.push(end);
	while (!q.empty()) {
		city* temp = q.front();
		q.pop();
		for (int x = 0; x < temp->adjacent.size(); x++) {
			if (temp->adjacent[x]->v->visited == false) {
				temp->adjacent[x]->v->visited = true;
				cout << temp->adjacent[x]->v->key << endl;
				q.push(temp->adjacent[x]->v);
			}
		}
	}
}

/*
* Method name: dijkstras
* Purpose: performs the book's description of Dijkstra's algorithm
* 	to find the shortest path from start to end
* @param start - the city name we are starting from
* @param end - the city name that we are searching for
* @return - the pointer to the end vertex
* Notes - use the pseudocode from the book. You can feel free to create
* 	another vector to use to keep track of solved vertices
*/
city* graph::dijkstras(string start, string end)
{
	city* startV = search(start);
	city* endV = search(end);
	startV->solved = true;
	startV->distance = 0;
	vector<city*> solved = { startV };
	while (!endV->solved) {
		int minDistance = INT_MAX;
		city* solvedV = NULL;
		city* parent = NULL;
		for (int x = 0; x < solved.size(); x++) {
			city* s = solved[x];
			for (int y = 0; s->adjacent.size(); y++) {
				if (!s->adjacent[y]->v->solved) {
					int distance = s->distance + s->adjacent[y]->weight;
					if (distance < minDistance) {
						solvedV = s->adjacent[y]->v;
						minDistance = distance;
						city* parent = s;
					}
				}
			}
		}
		solvedV->distance = minDistance;
		solvedV->parent = parent;
		solvedV->solved = true;
		solved.push_back(solvedV);
	}
	return endV;
}
#endif
