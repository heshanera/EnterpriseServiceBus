/* 
 * File:   Fibonacci.h
 * Author: heshan
 *
 * Created on June 1, 2017, 9:46 PM
 */

#ifndef FIBONACCI_H
#define FIBONACCI_H

#include <iostream>

class Fibonacci {
public:
    Fibonacci();
    Fibonacci(const Fibonacci& orig);
    virtual ~Fibonacci();
    
    int fib(int);
    int calcfib(int, int *);
    std::string run(int);
    
private:

};

#endif /* FIBONACCI_H */

