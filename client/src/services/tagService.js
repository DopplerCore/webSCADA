import axios from 'axios';

const API_URL = 'http://localhost:5000/api';

export const updateTagValue = async (tagId, newValue) => {
  try {
    const response = await axios.post(`${API_URL}/tags/${tagId}`, { value: newValue });
    return response.data;
  } catch (error) {
    console.error('Error updating tag:', error);
    if (error.response) {
      console.error('Server error:', error.response.status, error.response.data);
      throw new Error(`Server error: ${error.response.status} ${error.response.data.error || ''}`);
    } else if (error.request) {
      console.error('No response from server');
      throw new Error('No response from server. Please check if server is running.');
    } else {
      console.error('Request error:', error.message);
      throw new Error(`Request error: ${error.message}`);
    }
  }
};

export const fetchTags = async () => {
  try {
    const response = await axios.get(`${API_URL}/tags`);
    return response.data;
  } catch (error) {
    console.error('Error fetching tags:', error);
    throw error;
  }
};
