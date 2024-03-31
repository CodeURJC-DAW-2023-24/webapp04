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


### David Cereceda Catalán
In this phase I have been mainly responsible for registering users and saving them in the database, for correcting things that were missing from the previous phase, and for helping with tasks that my colleagues were developing.

[Commit 1](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/f6ecaafe4bff220c3b386bf406786c87805affd8)

[Commit 2](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/f38515b384cad46d5ed837f424f9e4759881b5e6)

[Commit 3](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/c089d723c01f1a90b1815cf6f049d5af7366ea16)

[Commit 4](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/ed0b45cc0447288a4822ab04bd177740960be0f3)

[Commit 5](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/d76b21c8bba819a883002c57c349d98c8f1a838b)

[File 1](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/java/webapp4/main/controller/AccountCreationController.java)

[File 2](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/resources/templates/register_page.html)

[File 3](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/java/webapp4/main/security/SecurityConfig.java)

[File 4](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/resources/static/public/js/profile_ajax.js)

[File 5](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/resources/templates/profile_page.html)
### Cristian Andrei Vlad
I was responsible for the database system with all the .csv, the AJAX part and some pages and most of the controllers
My 5 most important commits are:
- [Merge pull request #13 from CodeURJC-DAW-2023-24/Cristian](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/b0ad2ada02a61f0c00d2563e0322676f77e07b8d)
  
- [Merge pull request #10 from CodeURJC-DAW-2023-24/Cristian](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/b40cb7b0d526177c7f89136c2e14397a751de434)
  
- [Merge pull request #4 from CodeURJC-DAW-2023-24/client_private_area](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/cf1770215e6b37d02fb37466804fcfadeb20abbb)
  
- [TRANSFERS](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/5c317cf3110c426ecdbd5814dcd1a6bd8c651e87)
  
- [CALCULATE LOAN](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/0b1b417641bbb58fd771103ecaf5d12af88a78c5)
The 5 files in which I participated the most:
- [DBInitializer.java](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/java/webapp4/main/service/DBInitializer.java)
  
- [ProfileController.java](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/java/webapp4/main/controller/ProfileController.java)
  
- [DynamicContentController.java](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/java/webapp4/main/controller/DynamicContentController.java)
  
- [LoanRequestController.java](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/java/webapp4/main/controlle/LoanRequestController.java)
  
- [StaticResourceConfig.java](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/java/webapp4/main/controller/StaticResourceConfig.java)

### Julio del Junco Prieto

I was responsible for the security part of the project, as well as, helping with the controllers logic and all the dependencies of the pom.xml
My 5 most important commits are:
- [Merge pull request #9 from CodeURJC-DAW-2023-24/JuliodelJunco-patch-1](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/696b79e74e0480e11ff62149537fe2dce7aeacf8)
  
- [Merge pull request #8 from CodeURJC-DAW-2023-24/Cristian](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/43fab24b2321f2de4a05c6e5c42b053031780978)
  
- [Merge pull request #7 from CodeURJC-DAW-2023-24/JuliodelJunco-patch-1](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/2d9d3c1ca3631dcb7816e29a8bae37b87f22268a)
  
- [Merge pull request #6 from CodeURJC-DAW-2023-24/JuliodelJunco-patch-1](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/a613ad2bcda27151a82140f3e90923e8a87dcad2)
  
- [Merge pull request #5 from CodeURJC-DAW-2023-24/JuliodelJunco-patch-1](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/bb549998eb3addd4e55d7416722cb760877aa3c2)

The 5 files in which I participated the most:
- [pom.xml](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/pom.xml)
  
- [CSRFHandlerConfiguration.java](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/java/webapp4/main/security/CSRFHandlerConfiguration.java)
  
- [SecurityConfig.java](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/java/webapp4/main/security/SecurityConfig.java)
  
- [UserDetailsServiceImp.java](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/java/webapp4/main/security/UserDetailsServiceImp.java)
  
- [ProfileForwardController.java](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/java/webapp4/main/controller/ProfileForwardController.java)
### Sergio Lopez Cuesta
For this phase I was responsible on making the templates for make the application dynamic loading.

[Commit 1](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/ecd5ec346c7206fe6c88c8cffb98af75d66a4026): Modify profile page style.

[Commit 2](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/410f15d3a3183a6db733bce7f90b91f1a740ec87): Create loan request screen

[Commit 3](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/e072c2bc2099c6bc3300481d561c959e06e5f87d): Modify chart page, validation page.

[Commit 4](https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/e072c2bc2099c6bc3300481d561c959e06e5f87d): Create manager_header, modify_profile

[File1](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/resources/templates/profile_page.html): profile_page.html

[File2](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/resources/templates/loan_request_page.html):loan_request-html

[File3](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/resources/static/profile_header_manager.html): profile_header_manager.html

[File4](https://github.com/CodeURJC-DAW-2023-24/webapp04/blob/main/backend/src/main/resources/templates/validation_page.html): validation_page.html
### David Paul Limaylla Ticlavilca

[Commit_!]https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/700dbfa5a53a6f9833c400c5cfeb6110825fecce
[Commit_2]https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/47e09d32d74b389b8459fe7b7db2f9e42df0d535
[Commit_3]https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/c0f6945cf8a02aa09714832f850a0230f539239a
[Commit_4]https://github.com/CodeURJC-DAW-2023-24/webapp04/commit/a382119fc56bfd816c8ecf78ab7d2b5b50185156




<br>

---

© 2024 URJC Bank

Licensed under the Apache License, Version 2.0 - further details in the file [LICENSE](LICENSE).
