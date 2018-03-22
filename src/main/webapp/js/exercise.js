$(document).ready(() => {
    $.getJSON("./data/exercise.json", (response) => {
    //append row for each item in response array
        $('.exerciseType').text(response.type);
        $('.exerciseTopic').text(response.topic);
        $('.exerciseDescription').text(response.description);
        $('.exerciseDocument').attr("href", response.document)

        $('#exerciseList').append(
            $.map(response.exercises, (exercise) => {
                return '<tr>'
            	    + '<td>'
                	+ exercise.id
                	+ '</td><td>'
                	+ exercise.name
            	    + '</td><td>-> '
            	    + exercise.steps.join('<br/>-> ')
                	+ '</td></tr>';
            }).join());
        }).fail(() => {
            alert("error");
        });
    });