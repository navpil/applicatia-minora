# Applicatia Minora

This is a "Work" project where I solved various minor tasks without any intention of redistributing it.

Usually the code which does not deserve a separate repo is added here

## Simulations

`sim` package contains simulations, such as simulating a forest fire or a sand avalanche.
Use Swing for visualization.

 - AvalancheSimulation
 - BurnTreesSimulation

## jpanelcalc

Once I wrote my own Layout for swing applications.
The `jpanelcalc` package shows the underlying calculation for that layout.

The problem with `GridBagLayout` in Swing was that it was not versatile enough.
In particular it was not possible to set maximum/minimum width and ratio for every panel involved.

So I wrote my own. 
While the layout is not present in this code, but the calculation is here.

I don't remember if I extended the GridBagLayout, or I copied its code and changed where necessary.

## Quine

Prints out itself.

## chords

Project to test just tuning and show its problems.

## dice

Trying to find a combination for 6D dice faces, which would fit into the "four coins pattern".

When you drop four coins, you have these probabilities:

 - All heads / all tails - 1/16 each
 - One head / one tail - 1/4 each
 - Two heads + two tails - 3/8

Is it possible to simulate it with 6D dice?

What about "three coins pattern"?

The "coin patterns" are used in ancient games, such as Bul or Game of Ur.

## Ldap To Sql

LDAP is a NoSQL database which stores data in tree format and is optimized for reading.

Unfortunately documentation on LDAP protocol is often very limited and hard to find.
Once I understood that LDAP queries are like SQL queries, I decided to write a simple parser
which would translate LDAP queries to something SQL-lover would understand.

NB: this is a pseudo SQL and no LDAP server will understand this request.
