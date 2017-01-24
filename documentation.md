Cyber Security Base Project I

The idea of this exercise was to create a web application with at least five different security flaws from the OWASP top ten list (https://www.owasp.org/index.php/Top_10_2013-Top_10).

I made my application using the starter template, so the language is Java. You can copy/clone the application from Github, the address is https://github.com/EKettu/CSecProject1. The application is very simple (and of course unsafe), the idea is that people are able to choose from two events and sign up to one event. There are also two event organisers (Charlie, password “snoopy” and Jon, password “garfield”), who are able to log in to see who has signed up to their event. Organisers can delete unwanted people and also give VIP statuses to people of their choosing. There are several security issues with the application, first I’ll address the vulnerabilities that can be found using OWASP ZAP. 