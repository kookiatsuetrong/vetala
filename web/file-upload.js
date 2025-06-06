var FileUpload = { 
	type: "*",
	text: "File Upload",
	multiple: true,
	border: ".15rem dashed #888",
	background: "rgba(92%, 92%, 92%, .8)"
}

FileUpload.setType = function setType(mime) {
	FileUpload.type = mime
}

FileUpload.setText = function setText(message) {
	FileUpload.text = message
	var dropArea = document.querySelector(FileUpload.element)
	var p = dropArea.querySelector("p")
	p.innerText = FileUpload.text
}

FileUpload.setMultiple = function setMultiple(value) {
	FileUpload.multiple = value
}

FileUpload.setStyle = function setStyle(style) {
	for (var k in style) {
		FileUpload[k] = style[k]
	}
	
	var dropArea = document.querySelector(FileUpload.element)
	var p = dropArea.querySelector("p")
	
	for (var k in style) {
		dropArea.style[k] = style[k]
		if (k == "color") p.style[k] = style[k]
	}
}

FileUpload.create = function setup(element) {
	FileUpload.element = element
	var dropArea = document.querySelector(FileUpload.element)
	
	var p = document.createElement("p")
	dropArea.appendChild(p)
	p.innerText = FileUpload.text
	
	dropArea.style.height        = "3rem"
	dropArea.style.paddingBottom = "1rem"
	dropArea.style.display       = "block"
	dropArea.style.background    = FileUpload.background
	dropArea.style.border        = FileUpload.border
	dropArea.style.borderRadius  = "0.35rem"
	dropArea.style.transition    = "border .1s linear"
	dropArea.style.textAlign     = "center"
	
	dropArea.style.borderColor = FileUpload.borderColor

	dropArea.ondrop      = this.onDrop
	dropArea.onclick     = this.onClick
	dropArea.ondragover  = this.onDragOver
	dropArea.ondragleave = this.onDragLeave
	dropArea.ondragend   = this.onDragEnd
	
	dropArea.querySelector("p").style.color = "#666"
}

FileUpload.onDragOver = function onDragOver(event) {
	event.preventDefault()
	var dropArea = document.querySelector(FileUpload.element)
	dropArea.style.border = ".15rem dashed #bbb"
	dropArea.style.background = "#f2fff2"
}

FileUpload.onDragLeave = function onDragLeave(event) {
	event.preventDefault()
	var dropArea = document.querySelector(FileUpload.element)
	dropArea.style.border = FileUpload.border
	dropArea.style.background = FileUpload.background
}

FileUpload.onDragEnd = function onDragEnd(event) {
	event.preventDefault()
	var dropArea = document.querySelector(FileUpload.element)
	dropArea.style.border = FileUpload.border
	dropArea.style.background = FileUpload.background
}

FileUpload.onReady = function onReady(event) {
	var count = 0
	var dropArea = document.querySelector(FileUpload.element)
	var all = dropArea.querySelectorAll("[type=file]")
	for (var e of all) {
		for (var f of e.files) {
			count++
		}
	}
	dropArea.style.border = FileUpload.border
	dropArea.style.background = FileUpload.background
	
	var message = count + " File(s)"
	dropArea.querySelector("p").innerText = message
}

FileUpload.onDrop = function onDrop(event) {			
	event.preventDefault()
	FileUpload.uploadFiles(event.dataTransfer.files)
}

FileUpload.onClick = function onClick() {
	var chooser = document.createElement("input")
	chooser.type     = "file"
	chooser.multiple = FileUpload.multiple
	chooser.accept   = FileUpload.type
	chooser.capture  = true
	chooser.onchange = event => {
		FileUpload.uploadFiles(event.target.files)
	}
	chooser.click()
}

FileUpload.uploadFiles = function uploadFiles(files) {
	var random = document.querySelectorAll("input").length + 1
	var chooser = document.createElement("input")
	chooser.name = "photo-" + random
	chooser.type = "file"
	chooser.multiple = FileUpload.multiple
	// chooser.accept   = FileUpload.type
	chooser.style.display = "none"
	chooser.files = files

	var form = document.querySelector(FileUpload.element)
	form.appendChild(chooser)
	this.onReady(event)
}
