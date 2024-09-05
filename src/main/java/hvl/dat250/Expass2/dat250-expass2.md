
### Expass 2 Report

Throughout this whole project everything went quite smoothly. Because we had already downloaded
mostly everything we needed in expass 1, the only thing i needed to download
was Bruno. The reason why i chose to go with Bruno was because although i'm 
used to curl, i much prefer not using the terminal, and with Bruno, thing went much quicker.

The part that i had the most trouble with was actually understanding the task and how the
different classes are related to each other. It wasn't until much later after reading it over 
and over again that i actually understood that it's just a user posting a poll with different 
options that people can vote for. After understanding the task, things went much faster after 
creating an actual plan and the relationship of the classes as i understood it instead of me 
just coding something and deleting it because i didn't quite understand the task. 

Another challenge that i had was actually deciding what paths to use for each class. It was
more difficult than i imagined to create correct paths, where based on the path, you can
see the relationship between the classes. Another fact of this was actually deciding whether
it's better to have a variable as a RequestParam or as a PathVariable. In the end i ended up using both
based on what the variable was and how it fit within the relationship and CRUD operations. So most
get requests would use PathVariable, while most post request would use RequestParam since
we don't have that variable from before.

Another problem that i had was understanding aggregation and using the @JsonIgnore etc. I didn't quite understand
how they fit together. After researching online i attempted something and although i've attempted it, 
i'm sure that it could be done better than the solution i came up with.

There's plenty of things that i haven't been able to do to make the application a bit better. This includes things
like the automatic tests failing for one reason or another, when we test the application manually, things work just fine.
