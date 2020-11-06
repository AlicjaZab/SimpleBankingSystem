**Simple Banking System**

Simple simulation of a banking system using a database, made under the supervision of JetBrains Academy (https://hyperskill.org/projects/93?track=1).

While running application from terminal, you should provide the name of a file with database to operate on, after an `-fileName` argument, e.g. `-fileName db.s3db`. It can be an existing one.

After that, there is displayed a main menu. From that vieew you can create new account, log into account, and exit.

After choosing to create a new card there is displayed a card number and pin to the created card.

If you know your card number and pin, you can log into account. After doing that, you have acces to more options. You can:
* Display your balance
* Add income
* Do transfer
* Close (delete) account
* Log out
* Exit

Exaple of an application input and output:
```
1. Create an account
2. Log into account
0. Exit
>1

Your card have been created
Your card number:
4000009455296122
Your card PIN:
1961

1. Create an account
2. Log into account
0. Exit
>1

Your card have been created
Your card number:
4000003305160034
Your card PIN:
5639

1. Create an account
2. Log into account
0. Exit
>2

Enter your card number:
>4000009455296122
Enter your PIN:
>1961

You have successfully logged in!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
>2

Enter income:
>10000
Income was added!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
>1

Balance: 10000

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
>3

Transfer
Enter card number:
>4000003305160034
Enter how much money you want to transfer:
>5000
Success!

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit
>1

Balance: 5000

1. Balance
2. Add income
3. Do transfer
4. Close account
5. Log out
0. Exit

>0
Bye!
```