# Carbon Footprint Tracker

### UBC CPSC 210

**Track your impact on the climate with a carbon footprint calculator. Monitor how each part
of your daily life is contributing to climate change from your daily lifestyle choices.** 
 
 This application is aimed to be used by the average person to provide insight into their impact on the climate.
 The application aims to promote awareness and guidance towards a sustainable future. 
 I started this project to educate myself on urgent climate matters and enlighten others towards the relevancy of sustainability.
  
 
***Features***
- Calculate your yearly carbon footprint based on your daily activities
- Compare your carbon footprint to the average person in your country
- Receive a personalized chart of where your carbon emissions are coming from
- Receive tips on where to lessen your carbon emissions

### User Stories
- As a user, I want to be able to add a source of carbon emission to my emissions log
- As a user, I want to be able to view personalized emission calculations for my lifestyle
- As a user, I want to be able to receive information on reducing my emissions
- As a user, I want to be able to compare my carbon footprint to the average person in my country
- As a user, I want to be able to save my carbon emission records to file
- As a user, I want to be able to load my current carbon footprint to file and past carbon footprints when the program starts

### Instructions for Grader
- You can generate the first required event by going to the "Calculate" tab, where you can select a 
menu item and drag sliders to edit your current carbon footprint. You should see that making edits in the "Calculate" tab
will affect your carbon footprint in the "Overview" tab.
- You can generate the second required event by going to the "Overview" tab, where you can find graphs and data 
displaying the carbon emission sources added to your carbon footprint tracker
- You can locate my visual component by clicking on the "Overview" tab or the "Get results" button from the "Calculate" tab, 
where you will find different types of graphs summarizing the data you have entered. You can find more visual components 
in the "Take Action" tab.
- You can save the state of my application by clicking the "save" button in the "Calculate" tab
- You can reload the state of my application by restarting the program. You should find that the data in the graphs will describe 
your last save

### Phase 4: Task 2
I have included a type hierarchy in my code.  
CarbonEmission is an abstract class that is extended by Diet, HomeEnergy, Transportation, 
and Vehicle.  
Note: After Task 3, CarbonEmission is now an interface that is implemented by Diet, HomeEnergy, Transportation, and Vehicle

### Phase 4: Task 3
- The Transportation and Vehicle classes had high coupling as their code was nearly identical.  
I fixed this by extracting an abstract class, Travel, from their identical methods and making them both extend this new class.   
To do this, I also had to make CarbonEmission an interface. 
- The CarbonFootprintLog class had poor cohesion, as it was handling behaviour related to the date.  
I fixed this by making a Date class to specifically handle functionality related to the date
- There was some unnecessary coupling between the CarbonFootprintApp and CarbonEmission classes  
I fixed this by removing the CarbonEmission objects as fields, as they did not need to be there.
