/**
 * Extension to the standard Granite(not Coral) Pathbrowser or Pathfield component. It fetches the selected link title and
 * added it to the link label or title
 *
 * How to use:
 *
 * - add the class cq-dialog-link-to-title to the Granite(not Coral) Pathbrowser or Pathfield element
 * - add the data attribute cq-dialog-link-to-title-target to the specified element, value should be the
 *   selector, usually a specific class name, to find all possible target elements that can be modified in value.
 * - add the target class to each target component that holds the title of the link.
 */
(function(document, $) {
    "use strict";

    // when dialog gets injected
    $(document).on("foundation-contentloaded", function(e) {
        // if there is already an inital value make sure the according target element becomes visible
        $(".cq-dialog-link-to-title").each( function() {
            var value = $(this).find("[type='text']").val();
            if(!value){
                fetchTitle($(this));
            }
        });

    });

    $(document).on("change", ".cq-dialog-link-to-title", function(e) {
        fetchTitle($(this));
    });

    function fetchTitle(el){
        // get the selected value
        var value = el.find("[type='text']").val();

        // get the selector to find the target elements. its stored as data-.. attribute
        var target = el.data("cqDialogLinkToTitleTarget");

        if(value){
            $.ajax({
                url: '/bin/public/abbvie-pro/fetch-link-Title',
                type: 'GET',
                data: {
                    'link' : value
                },
                success: function (title) {
                    if(title){
                        $(target).val(title);
                    }
                },
                error: function(request, error){
                    console.log("Request: " + JSON.stringify(request) + "\n" + "Error: " + JSON.stringify(error));
                }
            });
        }

    }

})(document,Granite.$);
