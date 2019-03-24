$(function() {
    $('#homePageButton1').click(function() {
    	var title = document.getElementById('homePageFormEventTitle').value;
    	var startDate = document.getElementById('homePageFormEventStartDate').value;
    	var endDate = document.getElementById('homePageFormEventEndDate').value;
    	var startTime = document.getElementById('homePageFormEventStartTime').value.toString();
    	var endTime = document.getElementById('homePageFormEventEndTime').value.toString();
    	
    	var startMonth = startDate.split('/', 1)[0].toString();
    	var startDay = startDate.split('/', 2)[1].toString();
    	var startYear = startDate.split('/', 3)[2].toString();
    	
    	var endMonth = endDate.split('/', 1)[0].toString();
    	var endDay = endDate.split('/', 2)[1].toString();
    	var endYear = endDate.split('/', 3)[2].toString();
    	
    	var startHour = "";
    	var startMinute = "";
    	var endHour = "";
    	var endMinute = "";
    	
    	var invalid = false;
    	
    	var correctFormat = /[0-9]{2}:[0-9]{2}/;
    	console.log(startTime);
    	console.log(endTime);
    	
    	if (!correctFormat.test(startTime) || !correctFormat.test(endTime.trim()))
		{
    		invalid = true;
    		if (!correctFormat.test(startTime))
			{
    			console.log("start time not correct");
			}
    		
    		if (!correctFormat.test(endTime))
			{
    			console.log("end time not correct");
			}
		}
    	else
		{
    		console.log("correct format");
		}
    	
    	if (!invalid)
		{
    		startHour = startTime.split(':', 1)[0].toString();
    		startMinute = startTime.split(':', 2)[1].toString();
    		endHour = endTime.split(':', 1)[0].toString();
    		endMinute = endTime.split(':', 2)[1].toString();
    		
    		if (startHour > 23 || endHour > 23)
			{
    			invalid = true;
			}
    		
    		if (startMinute > 59 || endMinute > 59)
			{
    			invalid = true;
			}
		}
    	
		document.getElementById('homePageFormEventTitle').value = "Event Title";
		document.getElementById('homePageFormEventStartDate').value = "Start Date";
		document.getElementById('homePageFormEventEndDate').value = "End Date";
		document.getElementById('homePageFormEventStartTime').value = "Start Time";
		document.getElementById('homePageFormEventEndTime').value = "End Time";
		
		var event = {
				  'summary': title,
				  'location': '',
				  'description': '',
				  'start': {
				    'dateTime': startYear + '-' + startMonth + '-' + startDay + 'T' + startTime + ':00-07:00',
				    'timeZone': 'America/Los_Angeles'
				  },
				  'end': {
					'dateTime': endYear + '-' + endMonth + '-' + endDay + 'T' + endTime + ':00-07:00',
				    'timeZone': 'America/Los_Angeles'
				  },
				  'recurrence': [
				    //'RRULE:FREQ=DAILY;COUNT=2'
				  ],
				  'attendees': [
				    //{'email': 'lpage@example.com'},
				    //{'email': 'sbrin@example.com'}
				  ],
				  'reminders': {
				    //'useDefault': false,
				    //'overrides': [
				     // {'method': 'email', 'minutes': 24 * 60},
				      //{'method': 'popup', 'minutes': 10}
				    //]
				  }
				};
		
		if (!invalid)
		{
			gapi.client.calendar.events.insert({
				  'calendarId': 'primary',
				  'resource': event
				});
			console.log("inserted event");
		}
		
		console.log("end of function");
    });
});