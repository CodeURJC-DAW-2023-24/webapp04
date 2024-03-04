# URJC Bank

![URJC Bank logo](pics/extra/URJC_bank.png)


<h2>Index</h2>

- [A brief introduction](#intro)
- [Our development team](#dev_team)
- [Web features](#web_features)
    - [Entities](#web_entities)
    - [User permissions](#web_permissons)
    - [Images](#web_images)
    - [Charts](#web_charts)
    - [Complementary technology](#web_comp_tech)
    - [Advanced algorithms](#web_algos)
- [Extra information](#extra_info)

<br>

<h2 id="intro">📖 A brief introduction 📖</h2>
<h3>What is URJC Online Bank?</h3>

URJC Online Bank is a web application which allows their users to receive and tranfer funds, as well as to organize their customers' expenses.


<br>
<h2 id="dev_team">👩‍💻 Our development team 👨‍💻</h2>

| Member | Mail | Git username |
|:-:|:-:|:-:|
|Cristian Andrei Vlad|ca.vlad.2021@alumnos.urjc.es|[Cristian1A](https://github.com/Cristian1A)|
|David Cereceda Catalán|d.cereceda.2021@alumnos.urjc.es| [DavidCCGithubURJC](https://github.com/https://github.com/DavidCCGithubURJC)|
|Julio del Junco Prieto|j.deljunco.2021@alumnos.urjc.es|[JuliodelJunco](https://github.com/JuliodelJunco)|
|Sergio Lopez Cuesta|s.lopezc.2018@alumnos.urjc.es|[sergio2000-hub](https://github.com/sergio2000-hub)|
|David Paúl Limaylla Ticlavilca|dp.limaylla.2021@alumnos.urjc.es|[DavidPaul-LT](https://github.com/DavidPaul-LT)|

<br>

<h2 id="web_features">💻 Web features 💻</h2>

<h3 id="web_entities">🤖 Entities</h3>

🔸 **Users**

Anonymous users, clients, manager.

🔸 **Account**


🔸 **Transfer**


🔸 **Charges**

<br>

Bank clients have accounts, which contains a list of transfers and charges.

<hr>

<h3 id="web_permissons">🔐 User permissions</h3>

| Feature | Unregistered User | Bank client | Manager |
|:-:|:-:|:-:|:--|
|View information about offers, accounts and cards|✅|✅|✅|
|View relevant statistics about bank |✅|✅|✅|
|Ask for account creation|✅|❌|❌|
|Access the online banking tab|❌|✅|✅|
|Send and receive money|❌|✅|❌|
|Modify your profile|❌|✅|✅|
|View information about deposited and expended money|❌|✅|✅|
|Apply for a loan|❌|✅|❌|
|Approve the creation of an account|❌|❌|✅|
|Block access to an account or the funds in it|❌|❌|✅|


<hr>

<h3 id="web_images">🖼️ Images</h3>

🔸 **Main page images**
        ![image](https://github.com/CodeURJC-DAW-2023-24/webapp04/assets/116211485/ffca8136-d8c2-432f-bfdb-c73cfbdf07b5)
🔸 **User profile pictures**    
    ![image](https://github.com/CodeURJC-DAW-2023-24/webapp04/assets/116211485/8d593fd0-18ea-4963-b14a-667c44be6123)

🔸 **Charge type image**
    ![image](https://github.com/CodeURJC-DAW-2023-24/webapp04/assets/116211485/449ab329-5567-4234-b395-40509af1e9ba)

<hr>

<h3 id="web_charts">📊 Charts</h3>

🔸 **Income distribution chart**

This is a bar chart that indicates how much money has been deposited over a period of time.

🔸 **Spending distribution chart**

This is a pie chart that classifies all types of spending and indicates how much money a client spends in each cathegory.

<hr>

<h3 id="web_comp_tech">🌐 Complementary technology</h3>

🔸 **map**

show location.

<hr>

<h3 id="web_algos">🧠 Advanced algorithms</h3>

🔸 **Charge filter system**

An algorithm will be implemented to allow users to view their expenses filtered by the expense type.

<br>

<h2 id="extra_info">ℹ️ Extra information ℹ️</h2>

- [Trello](https://trello.com/b/PW1FfBkP/daw-grupo-4)

# PHASE 2

## Navigation
![Captura desde 2024-03-04 07-29-35](https://github.com/CodeURJC-DAW-2023-24/webapp04/assets/116211485/9a9d19f5-9003-4b11-80e1-ea5370e1d2b7)

<br>

## Implementation instructions

For linux distributions and Java version 17:

1. Clone the repository by typing in the command shell `git clone https://github.com/CodeURJC-DAW-2023-24/webapp04`
2. Create a database in MySQL called *urjc_bank*
3. Run BackendApplication's main function
4. Open `https://localhost:8443/` in any browser
<br>

## Diagram database

![image](https://github.com/CodeURJC-DAW-2023-24/webapp04/assets/116211485/00174534-cd6c-4786-bcdd-d3fc31770689)

<br>

## Diagram entities

![image](https://github.com/CodeURJC-DAW-2023-24/webapp04/assets/116211485/93a44059-147f-4d57-a645-27299bc1111c)

<br>

## Member participation

### Cristian Andrei Vlad

### David Cereceda Catalán

[Commit 1](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/f6ecaafe4bff220c3b386bf406786c87805affd8)

[Commit 2](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/f38515b384cad46d5ed837f424f9e4759881b5e6)

[Commit 3](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/c089d723c01f1a90b1815cf6f049d5af7366ea16)

[Commit 4](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/ed0b45cc0447288a4822ab04bd177740960be0f3)

[Commit 5](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/d76b21c8bba819a883002c57c349d98c8f1a838b)

[Fichero 1](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/java/webapp4/main/controller/AccountCreationController.java)

[Fichero 2](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/resources/templates/register_page.html)

[Fichero 3](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/java/webapp4/main/security/SecurityConfig.java)

[Fichero 4](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/resources/static/public/js/profile_ajax.js)

[Fichero 5](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/resources/templates/profile_page.html)
### Julio del Junco Prieto

### Sergio Lopez Cuesta

### David Paúl Limaylla Ticlavilca


<br>

---

© 2024 URJC Bank

Licensed under the Apache License, Version 2.0 - further details in the file [LICENSE](LICENSE).
