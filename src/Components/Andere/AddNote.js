import React, { useState } from 'react';
import { Card, CardContent, Typography, TextField, Button, Grid } from '@mui/material';
import { jwtDecode } from 'jwt-decode';
function AddNote() {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [tags, setTags] = useState('');
  const [images, setImages] = useState([]);
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleImageUpload = (event) => {
    const files = Array.from(event.target.files);
    const newImages = files.map((file) => {
      if (file.size > 2 * 1024 * 1024) {
        alert('File size must be less than 2MB.');
        return null;
      }
      if (!file.type.startsWith('image/')) {
        alert('Only image files are allowed.');
        return null;
      }
      return {
        file,
        preview: URL.createObjectURL(file),
      };
    }).filter(Boolean);
    setImages((prevImages) => [...prevImages, ...newImages]);
  };

  const handleRemoveImage = (index) => {
    setImages((prevImages) => prevImages.filter((_, i) => i !== index));
  };

  const handleSaveNote = async () => {
    if (!title || !description) {
      alert('Title and description are required!');
      return;
    }

    const formData = new FormData();
    formData.append('title', title);
    formData.append('description', description);
    formData.append('tags', tags);

    const token = localStorage.getItem('token');
    if (!token) {
      alert('User is not authenticated!');
      return;
    }

    const decodedToken = jwtDecode(token);
    const userId = decodedToken.userId;
    const userName = decodedToken.userName;
    console.log(`User ID: ${userId}, User Name: ${userName}`);

    formData.append('userId', userId);
    images.forEach((image) => {
      formData.append('images', image.file);
    });

    setIsSubmitting(true);

    try {
      const response = await fetch('http://192.168.178.186:8080/notes', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`,
        },
        body: formData,
      });

      if (response.ok) {
        const responseData = await response.json();
        alert('Note saved successfully!');
        console.log('Saved Note:', responseData);
        setTitle('');
        setDescription('');
        setTags('');
        setImages([]);
      } else {
        const errorData = await response.json();
        alert(`Failed to save note: ${errorData.message || 'Unknown error'}`);
      }
    } catch (error) {
      console.error('Error saving note:', error);
      alert('An error occurred while saving the note.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Card>
      <CardContent>
        <Typography variant="h5" gutterBottom>
          Add New Note
        </Typography>
        <TextField
          label="Title"
          fullWidth
          margin="normal"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
        <TextField
          label="Description"
          multiline
          rows={4}
          fullWidth
          margin="normal"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
        <TextField
          label="Tags"
          fullWidth
          margin="normal"
          value={tags}
          onChange={(e) => setTags(e.target.value)}
        />
        <div style={{ margin: '20px 0' }}>
          <Button variant="contained" component="label" color="primary">
            Upload Images
            <input
              type="file"
              accept="image/*"
              hidden
              multiple
              onChange={handleImageUpload}
            />
          </Button>
        </div>
        {images.length > 0 && (
          <Grid container spacing={2} style={{ marginTop: '20px' }}>
            {images.map((image, index) => (
              <Grid item xs={12} sm={6} md={4} key={index}>
                <div style={{ position: 'relative' }}>
                  <img
                    src={image.preview}
                    alt={`Uploaded ${index}`}
                    style={{
                      width: '100%',
                      height: 'auto',
                      maxHeight: '200px',
                      objectFit: 'contain',
                      border: '1px solid #ccc',
                      borderRadius: '4px',
                    }}
                  />
                  <Button
                    variant="contained"
                    color="secondary"
                    onClick={() => handleRemoveImage(index)}
                    style={{
                      position: 'absolute',
                      top: '5px',
                      right: '5px',
                      fontSize: '10px',
                      padding: '2px 5px',
                    }}
                  >
                    Remove
                  </Button>
                </div>
              </Grid>
            ))}
          </Grid>
        )}
        <Button
          variant="contained"
          color="secondary"
          style={{ marginTop: '20px' }}
          onClick={handleSaveNote}
          disabled={isSubmitting}
        >
          {isSubmitting ? 'Saving...' : 'Save Note'}
        </Button>
      </CardContent>
    </Card>
  );
}

export default AddNote;