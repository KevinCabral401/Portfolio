

#include <iostream>
#include <fstream> // for file IO
#include <typeinfo> // for typeid
#include <sstream> // for splitting strings
#include "graph.h" // our file for bst
using namespace std;

/*
* Main function (driver)
*/

int main()
{
	graph* cities = new graph();
	// write a loop to keep taking user choices
	bool ok = true;
	while (ok)
	{
		// prompt user for choice
		string choice;
		cout << "Enter a choice:\nAdd a city (1)\nInserting an edge (2)\nPrint Graph (3)\nBFT (4)\nDFT (5)\nDijkstra's (6)\nExit (7)" << endl;
		getline(cin, choice);
		cout << "You entered: " << choice << endl;
		// handle case 1: add a city
		if (choice == "1")
		{
			string cityName;
			cout << "Enter the name of the city: " << endl;
			getline(cin, cityName, '\n');
			cities->insertCity(cityName);
		}
		// handle case 2: add an edge
		else if (choice == "2")
		{
			string city1, city2, weight;
			cout << "Enter the name of first city: " << endl;
			getline(cin, city1, '\n');
			cout << "Enter the name of second city: " << endl;
			getline(cin, city2, '\n');
			cout << "Enter the weight: " << endl;
			getline(cin, weight, '\n');
			cities->insertEdge(city1, city2, stoi(weight));
		}
		// handle case 3: print graph
		else if (choice == "3")
		{
			cities->printGraph();
		}
		// handle case 4: bft
		else if (choice == "4")
		{
			string cityName;
			cout << "Enter the name of the city you want to start at: " << endl;
			getline(cin, cityName, '\n');
			cities->bft(cityName);
		}
		// handle case 5: dft
		else if (choice == "5")
		{
			string cityName;
			cout << "Enter the name of the city you want to start at: " << endl;
			getline(cin, cityName, '\n');
			cities->dft(cityName);
		}
		// handle case 6: Dijkstra's
		else if (choice == "6")
		{
			string start, end;
			cout << "Enter the name of the city you want to start at: " << endl;
			getline(cin, start, '\n');
			cout << "Enter the name of the city you want to search for: " << endl;
			getline(cin, end, '\n');
			city* endV = cities->dijkstras(start, end);
			cout << "Dijstra's returned a path of weight " << endV->distance << " from " << start << " to " << end << endl;
			// print out the path
			cities->printShortestPath(endV);
		}
		// handle case 7: exit
		else if (choice == "7")
		{
			cout << "EXITING" << endl;
			ok = false;
		}
		// handle invalid input by exiting
		else
		{
			cout << "Invalid choice, exiting!" << endl;
			ok = false;
		}
	}

	delete cities;
	return 0; //return "success" status
}