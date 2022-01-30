Although social skills come naturally to many, many others have to work hard to understand them. Such struggles with social interactions can cause one to feel different from those around them. Although being different is by no means a bad thing, as the uniqueness of each person is what makes the world so amazing, observing this fact because of a difficulty with social interaction can cause one to feel deeply alone. It has been my hope to work on a project to help such people for some time; this is that project. This application is meant to be a tool that people with such experiences can use to train and improve their social skills. More specifically, it uses a deep learning model to help such people better interpret the emotion implied by a person's facial expression. While it's nowhere near perfect, it should be good enough for the user to effectively learn how to understand the  emotion in a person's face, using a sort of "quiz" approach.

The idea for this particular project came to me in September 2021, however I've had to scrap several implementations since; the current source code is the first implementation I managed to get working. The application is made up of two components: a frontend GUI implemented with Java and JavaFX, and a backend which uses Python and the FER package to detect emotion from an uploaded image of a person's face. The command line is the bridge which connects the Java frontend and the Python backend; it feeds input from the frontend to the backend and output from the backend to the frontend. I understand that this is probably not the best way to connect the two components, however given the differences between the Java and Python languages, using the command line was the simplest way to do so. As I continue to work on this project, I'll be sure to research different ways to bridge the two languages to replace the current command line method.

This current build is not the final version, I'll most likely be working on this for some time. However, between work and school, it might be difficult to update as regularly as I'd prefer. Nonetheless, I'll make sure to work on improvements and update this repository whenever I can.