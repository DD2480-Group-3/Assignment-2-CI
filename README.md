# Assignment 2 : CI

## How to run :

Create a json file <em>Secret.json</em> which should have :

```json
{
  "github_username": "youremail@mail.com",
  "github_token": "tokenstring"
}
```

### To build :

````

gradle build

```

### To test :

```

gradle test

```

### To start the server :

```

gradle run

```

### We configure our Github repository:

- go to `Settings >> Webhooks`, click on `Add webhook`.
- paste the forwarding URL (eg `http://8929b010.ngrok.io`) in field `Payload URL` and send click on `Add webhook`. In the simplest setting, nothing more is required.

## Description

The Continuous Intergration server that we created is used in conjunction to Github. It helps to compile and test the builds pushed to git hub and notifies the user of the status of the build result via email.

## Contributions

| Name   | Contribution                                           |
| ------ | ------------------------------------------------------ |
| Kalle  |                                                        |
| Linus  |                                                        |
| Martin |                                                        |
| Burcu  |                                                        |
| JJ     | Wrote the Email Service with Javadocs and other issues |

## Way of working

| Way of working        | Details                                                                                                                                                                                             |
| --------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Principles            | We agreed that we should follow good coding practices as stated in the lectures, and practice them throughout our assignments.                                                                      |
| Foundations           | We had to create an issue everytime we wanted to edit the main branch and create a seperate branch to work on issue before creating a pull request to merge it back to main.                        |
| In Use                | We introduced Gradle, JUnit and JavaDocs to help us in our testing and documenting of the Assignment                                                                                                |
| <mark>In Place</mark> | The practice was adopted by all our team members and we worked well together to implement the deliverables. We do not hesistate to voice out our ideas if we feel that something could be improved. |

We see ourselves being in the Essence level <em>In Place</em>, as we have adopted the tools and practices as a team and everyone has access to them. During our first meeting we decdided not to use a more complicated workflow like CI, since all members were relatively new to using git. We instead kept to the basics and focused on learning the core features. For our next project we plan to have an initial discussion to implement a clearer/stricter way of working to further improve and focus more on feedback on the way of working to reach the next Essence level (<em>In Use</em>).
```
````
