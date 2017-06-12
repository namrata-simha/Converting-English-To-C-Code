# Converting Natural Language (English) To C code constructs 
In this project, I selected a particular construct from the C language and wrote a script in Scala to handle natural language (spoken English language) so as to obtain the C representation of the same, and further compiled and ran that C code from the same scala script. There has been a lot of work in the area of the use of natural language for the representation of code. This is done to the end of helping novice programmers and others who are unfamiliar with formal coding constructs. The basic idea of this project is to pick up code-style-logic from a simple English sentence. It is a monumental task to implement a whole language like this, so for a start, I selected only one construct, i.e the basic **if-then-else**. 

## Running Instructions:
* Start a new project in your Eclipse IDE for Scala. If you haven't already downloaded and set this up, [click here](http://scala-ide.org/) for the download link. If you need help setting it up and getting started, [here](https://www.youtube.com/watch?v=PtkNg4mK4NY) is a nice tutorial for the same!
* Add the 4 files in my [src](src/) folder to your project src folder and run the main as a Scala Application.
* Type in your sentence and check if the C code matches what you expected. The output of the C code will also be displayed.

## Restrictions on Input:
*Note: These restrictions are only in place because I haven't been able to set up a working NLP package to work with any other kinds of words in the sentence. Will add it soon*
* Must contain if-then-else or if-then for the code to work. The sentence needs to contain these words specifically. ("Otherwise" can be used instead of "else")
* Can only have sentences containing "I have", "She has" or "They had" or such with other pronouns only with respect to information being provided about variables in the sentence.
* Can contain only 2 consecutive input sentences.
* Can only use comparators >,< and == in the if conditions.
* No full-stop at the end of the last sentence in the input. (may it be the first or the second)
