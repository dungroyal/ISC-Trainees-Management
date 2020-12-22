import $ from 'jquery'; 

$( document ).ready(function() {
    console.log("OKEEEEEEEEEEEEEEE");

    $("#imageUpload").change(function() {
        console.log("UPload");
        // readURL(this);
    });

    $( "#cheeeeeeeeeeeeee" ).click(function() {
        alert( "Handler for .click() called." );
      });
  });


// function readURL(input) {
//     if (input.files && input.files[0]) {
//         var reader = new FileReader();
//         reader.onload = function(e) {
//             $('#imagePreview').css('background-image', 'url('+e.target.result +')');
//             $('#imagePreview').hide();
//             $('#imagePreview').fadeIn(650);
//         }
//         reader.readAsDataURL(input.files[0]);
//     }
// }
