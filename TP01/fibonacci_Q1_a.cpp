#include <iostream>
#include <string>
#include <cmath>

using namespace std;

int fibonacci_formula(int n){
	n--;
	int fibonacci_n = round(1/sqrt(5) * pow((1 + sqrt(5)) / 2, n) - 1/sqrt(5) * pow((1 - sqrt(5)) / 2, n));

    return fibonacci_n;
}

long long int* fibonacci_generic(int startig_value, int next_value, int counter){
	long long int* list_r = new long long int[counter];

	list_r[0] = startig_value;
	list_r[1] = next_value;

	for (int i = 2; i < counter; ++i){
		list_r[i] = list_r[i-1] + list_r[i-2];
	}

	return list_r;
} 

int main(int argc, char *argv[ ]) {

	int ini = atoi(argv[0]);
	int end = atoi(argv[1]);

	cout << "Este programa imprime parte da sequencia de fibonacci:\n do i-esimo ate o n-esimo numero\n";
	
	// cout << "i: ";
	// Entrada interativa
	// cin >> ini;
	// while(ini < 1){
	// 	cout << "i deve ser maior ou igual a 1.\n";
	// 	cin >> ini;
	// }

	// cout << "n: ";
	// Entrada interativa
	// cin >> end;
	// while(end < ini){
	// 	cout << "n deve ser maior ou igual a i.\n";
	// 	cin >> end;
	// }

	long long int* list = fibonacci_generic(fibonacci_formula(ini), 
						fibonacci_formula(ini+1), end-ini+1);

	cout << "fibonaccis de " << ini << " ate " << end << ":\n"; 

	string fibs ("");

	for (int i = 0; i < end - ini + 1; ++i){
		fibs += to_string(list[i]);
		fibs += " ";
	}

	fibs += "\n";

	cout << fibs;

	return 0;
}