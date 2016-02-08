

	function getGlossary(alphabet,property, path) {
		$('#paginationTop').hide();
		$('#paginationBottom').hide();
		$('#alphabetH1').hide();
		$('#glossaryCenter').hide();
		$('#loadingImg').css('visibility', 'visible');
		$('#loadingImg').css('display', 'block');
		$.ajax({
			type : 'GET',
			url : '/aemservices/bsr/getglossary',
			// all parameters added
			data : 'alphabet=' + alphabet+ '&path=' + path+'&property=' + property,
			success : function(msg) {
				var json = $.parseJSON(msg);
				$('#loadingImg').css('visibility', 'hidden');
				$('#loadingImg').css('display', 'none');
				$('#alphabetH1').empty().append(alphabet.toUpperCase());
				$('#alphabetH1').show();
				var jsonString = createListing(json,alphabet);
				$('#glossaryCenter').empty().append(jsonString);
				$('#glossaryCenter').show();
				var selectList = $('#selectList').val();
				if(selectList === 'both' || selectList === 'top') {
					var paginationStringTop = createPaginationTop(alphabet, path,json);
					$('#paginationTop').empty().append(paginationStringTop);
					$('#paginationTop').show();
				}
				if(selectList === 'both' || selectList === 'bottom') {
					var paginationStringBottom = createPaginationBottom(alphabet, path,json);
					$('#paginationBottom').empty().append(paginationStringBottom);
					$('#paginationBottom').show();
				}
			}
		});
	}

//	Method to create the glossary listing
	function createListing(json,alphabet) {
		var jsonString = '';
		if (json != null && json.glossary.length != 0) {
			var numOfIterations = json.glossary.length;
			for (iteration = 0; iteration < numOfIterations; iteration++) {
				var glossary = json.glossary[iteration];
				if (glossary != null) {
					var formattedJson = getFormattedJson(glossary,alphabet);
					jsonString = jsonString + formattedJson;
				}
			}
		}
		return jsonString;
	}


//	This method renders formats the raw JSON data in the required format to be displayed on JSP
	function getFormattedJson(glossary,alphabet) {
		var temp ='';
		if(alphabet.toLowerCase()==='all') {
			var temp = '<div id="glossarycenter"><b>' + glossary.word + '</b><br><p>' + glossary.description + '</p><br></div>';
		}
		if(glossary.word.toLowerCase().trim().charAt(0) === alphabet.toLowerCase()) {
			var temp = '<div id="glossarycenter"><b>' + glossary.word + '</b><br><p>' + glossary.description + '</p><br></div>';
		}
		return temp;
	}

//	Creating top pagination 
	function createPaginationTop(currentalphabet, path,json) {
		var arryOfFirstLetters = getFirstLetterOfGlossaryItems(json);
		var temp='';
		var all = "<a class='anchorNav' onclick='getGlossary(" + '\"' + 'all' + '\"' + ',' + '"glossary"' + ',' + '\"' + path + '\"' + ");'><li><strong>" + 'ALL' + "</strong></li></a>";
		for (var alphabetCode = 65; alphabetCode <= 90; alphabetCode++) {
				if($.inArray(String.fromCharCode(alphabetCode),arryOfFirstLetters)>-1 && (currentalphabet != String.fromCharCode(alphabetCode))) {
					temp = temp+"<a class='anchorNav' onclick='getGlossary(" + '\"' + String.fromCharCode(alphabetCode) + '\"' + ',' + '"glossary"' + ',' + '\"' + path + '\"' + ");'><li><strong>" + String.fromCharCode(alphabetCode) + "</strong></li></a>";
				}
				else if($.inArray(String.fromCharCode(alphabetCode),arryOfFirstLetters)>-1 && (currentalphabet === String.fromCharCode(alphabetCode))) {
					temp = temp+'<li><strong>'+String.fromCharCode(alphabetCode)+'</strong></li>';
				}
				//if($.inArray(alphabetCode,arryOfFirstLetters) < 0) {
				else {
					temp = temp+'<li><strong class="gray">'+String.fromCharCode(alphabetCode)+'</strong></li>';
				}
		}
		return '<ul id="navlist">'+temp+all+'</ul>';
	}

	
//	Creating top pagination 
	function createPaginationBottom(currentalphabet, path,json) {
		var arryOfFirstLetters = getFirstLetterOfGlossaryItems(json);
		var temp='';
		var all = "<a class='anchorNav' onclick='getGlossary(" + '\"' + 'all' + '\"' + ',' + '"glossary"' + ',' + '\"' + path + '\"' + ");'><li><strong>" + 'ALL' + "</strong></li></a>";
		for (var alphabetCode = 65; alphabetCode <= 90; alphabetCode++) {
				if($.inArray(String.fromCharCode(alphabetCode),arryOfFirstLetters)>-1 && (currentalphabet != String.fromCharCode(alphabetCode))) {
					temp = temp+"<a class='anchorNav' onclick='getGlossary(" + '\"' + String.fromCharCode(alphabetCode) + '\"' + ',' + '"glossary"' + ',' + '\"' + path + '\"' + ");'><li><strong>" + String.fromCharCode(alphabetCode) + "</strong></li></a>";
				}
				else if($.inArray(String.fromCharCode(alphabetCode),arryOfFirstLetters)>-1 && (currentalphabet === String.fromCharCode(alphabetCode))) {
					temp = temp+'<li><strong>'+String.fromCharCode(alphabetCode)+'</strong></li>';
				}
				//if($.inArray(alphabetCode,arryOfFirstLetters) < 0) {
				else {
					temp = temp+'<li><strong class="gray">'+String.fromCharCode(alphabetCode)+'</strong></li>';
				}
		}
		return '<ul id="navlist">'+temp+all+'</ul>';
	}
	
	
	//function to return array of first letters of the glossary items configured
	function getFirstLetterOfGlossaryItems(json) {
		var letterArray=[];
		var numOfIterations = json.glossary.length;
		for (iteration = 0; iteration < numOfIterations; iteration++) {
			var glossary = json.glossary[iteration];
			if (glossary != null) {
				letterArray.push(glossary.word.trim().charAt(0).toUpperCase());
			}
		}
		return unique(letterArray);
	}

	//function to remove duplicates from array
	function unique(list) {
		var result = [];
		$.each(list, function(i, e) {
			if ($.inArray(e, result) == -1) result.push(e);
		});
		return result;
	}

