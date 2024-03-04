fetch(`/image_loader`)
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.blob();
    })
    .then(blob => {
        const imageUrl = URL.createObjectURL(blob);
        const imgElement = document.getElementById('client_pic');
        imgElement.src = imageUrl;
    })
    .catch(error => {
        console.error('Error fetching image:', error);
    });
