import React, { useState } from 'react';
import { updateTagValue } from '../services/tagService';

const TagItem = ({ tag }) => {
  const [editValue, setEditValue] = useState(tag ? tag.value : 0);
  const [isEditing, setIsEditing] = useState(false);
  const [error, setError] = useState(null);

  if (!tag) {
    return <div>Invalid tag data</div>;
  }

  const handleEdit = () => {
    setIsEditing(true);
    setError(null);
  };

  const handleSave = async () => {
    try {
      await updateTagValue(tag.id, parseFloat(editValue) || editValue);
      setIsEditing(false);
      setError(null);
    } catch (error) {
      console.error('Failed to update tag:', error);
      setError('Failed to update tag');
    }
  };

  const handleCancel = () => {
    setEditValue(tag.value);
    setIsEditing(false);
    setError(null);
  };

  const getTagStyle = () => {
    if (tag.unit === 'state') {
      return {
        backgroundColor: tag.value === 1 ? '#4caf50' : '#f44336',
        color: 'white',
        padding: '8px',
        borderRadius: '4px',
        margin: '4px 0'
      };
    }
    return {};
  };

  return (
    <div className="tag-item" style={{ 
      border: '1px solid #ddd', 
      padding: '10px', 
      margin: '10px 0', 
      borderRadius: '5px',
      backgroundColor: '#f9f9f9'
    }}>
      <div className="tag-header" style={{ display: 'flex', justifyContent: 'space-between' }}>
        <h3>{tag.name}</h3>
        {!isEditing && (
          <button onClick={handleEdit} style={{ 
            backgroundColor: '#2196F3', 
            color: 'white',
            border: 'none',
            padding: '5px 10px',
            borderRadius: '3px',
            cursor: 'pointer'
          }}>
            Edit
          </button>
        )}
      </div>
      
      <div className="tag-value" style={getTagStyle()}>
        {isEditing ? (
          <div>
            <input
              type={tag.unit === 'state' ? 'number' : 'text'}
              value={editValue}
              onChange={(e) => setEditValue(e.target.value)}
              min={tag.unit === 'state' ? 0 : undefined}
              max={tag.unit === 'state' ? 1 : undefined}
              style={{ width: '100%', padding: '5px', marginBottom: '5px' }}
            />
            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
              <button onClick={handleSave} style={{ 
                backgroundColor: '#4CAF50', 
                color: 'white',
                border: 'none',
                padding: '5px 10px',
                borderRadius: '3px',
                cursor: 'pointer'
              }}>
                Save
              </button>
              <button onClick={handleCancel} style={{ 
                backgroundColor: '#f44336', 
                color: 'white',
                border: 'none',
                padding: '5px 10px',
                borderRadius: '3px',
                cursor: 'pointer'
              }}>
                Cancel
              </button>
            </div>
          </div>
        ) : (
          <span>
            {tag.value} {tag.unit}
          </span>
        )}
      </div>
    </div>
  );
};

export default TagItem;
