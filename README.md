# Differential Equations. Computational Practicum.

[F20] Differential Equations. BS19-02

## Description

* Given the initial value problem with the ODE of the first order and some interval:\
y'=f(x, y)\
y(x_0)=y_0\
x belongs to (x_0; X)

* Implement the following numerical methods:
  - Euler’s method
  - improved Euler’s method
  - Runge-Kutta method

## The application

* Provides data visualization capability (charts plotting) in the user interface using JavaFX

* Constructs a corresponding approximation of the solution of a given initial value problem and the exact solution of an IVP (with the possibility of changing the initial conditions).

* Investigates the convergence of these numerical methods on different grid sizes (with the possibility of changing the number of grid steps).

* Compares approximation errors of these methods plotting the corresponding chart for different grid sizes (with the possibility of changing the range of grid steps).

### Overview
![overview 1](readme_images/overview1.png)
![overview 2](readme_images/overview2.png)

#### Approximation
![approximation](readme_images/approximation.png)

#### GTE/LTE (Global/Local truncation error)
<p align="center">
<img width="300" src="readme_images/gte.png"/>
<img width="300" src="readme_images/lte.png"/>
</p>

### Total Approximation Error
![approximation](readme_images/total_approximation_error.png)

### UML diagram
![approximation](readme_images/uml.png)
