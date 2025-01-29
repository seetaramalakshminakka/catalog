#include <iostream>
#include <fstream>
#include <map>
#include <vector>
#include <nlohmann/json.hpp>
using json = nlohmann::json; 
using namespace std;

// Function to convert a number from a given base to decimal
long long baseToDecimal(const string &value, int base) {
    return stoll(value, nullptr, base);
}

// Function to perform Lagrange interpolation to find constant term (c)
double lagrangeInterpolation(vector<pair<int, long long>> points) {
    int k = points.size();
    double constant = 0;
    
    for (int i = 0; i < k; i++) {
        double term = points[i].second;
        
        for (int j = 0; j < k; j++) {
            if (i != j) {
                term *= -points[j].first * 1.0 / (points[i].first - points[j].first);
            }
        }
        
        constant += term;
    }
    return constant;
}

int main() {
    vector<string> filenames = {"testcase1.json", "testcase2.json"};
    
    for (const string &filename : filenames) {
        ifstream file(filename);
        if (!file) {
            cerr << "Error opening file: " << filename << endl;
            return 1;
        }

        json data;
        file >> data;

        int k = data["keys"]["k"];
        vector<pair<int, long long>> points;

        for (auto &el : data.items()) {
            if (el.key() == "keys") continue;
            
            int x = stoi(el.key());
            int base = stoi(el.value()["base"].get<string>());
            long long y = baseToDecimal(el.value()["value"].get<string>(), base);
            
            points.push_back({x, y});
            if (points.size() == k) break;
        }

        double secret = lagrangeInterpolation(points);
        cout << "Secret (c) for " << filename << ": " << (long long)secret << endl;
    }
    return 0;
}

