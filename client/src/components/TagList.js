import React from 'react';
import TagItem from './TagItem';

const TagList = ({ tags = [] }) => {
  if (!tags || tags.length === 0) {
    return (
      <div className="tag-list" style={{ textAlign: 'center', padding: '20px' }}>
        <h2>SCADA Tags</h2>
        <p>No tags available. Waiting for data...</p>
      </div>
    );
  }

  return (
    <div className="tag-list">
      <h2>SCADA Tags</h2>
      {tags.map(tag => (
        <TagItem key={tag.id} tag={tag} />
      ))}
    </div>
  );
};

export default TagList;
