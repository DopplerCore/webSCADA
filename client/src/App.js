import React, { useState, useEffect } from 'react';
import TagList from './components/TagList';
import axios from 'axios';

function App() {
  const [tags, setTags] = useState([]);
  const [wsConnected, setWsConnected] = useState(false);
  const [lastUpdate, setLastUpdate] = useState(null);
  const [error, setError] = useState(null);



  useEffect(() => {
    let ws = null;
    
    const connectWebSocket = () => {
      ws = new WebSocket('ws://localhost:5000');
      
      ws.onopen = () => {
        console.log('Connected to WebSocket server');
        setWsConnected(true);
        setError(null);
      };
      
      ws.onmessage = (event) => {
        try {
          const data = JSON.parse(event.data);
          console.log('Received data:', data);
          if (data && data.tags) {
            setTags(data.tags);
            setLastUpdate(new Date().toLocaleTimeString() + ' (WS)');
          }
        } catch (err) {
          console.error('Error parsing WebSocket message:', err);
        }
      };
      
      ws.onclose = (event) => {
        console.log('Disconnected from WebSocket server:', event.code, event.reason);
        setWsConnected(false);
        
        
        setTimeout(() => {
          connectWebSocket();
        }, 5000);
      };
      
      ws.onerror = (error) => {
        console.error('WebSocket error:', error);
        setError('WebSocket connection error');
      };
    };
    
    
    connectWebSocket();
    
    return () => {
      if (ws) {
        ws.close();
      }
    };
  }, []);

  return (
    <div className="App" style={{ 
      maxWidth: '800px', 
      margin: '0 auto', 
      padding: '20px',
      fontFamily: 'Arial, sans-serif'
    }}>
      <header style={{ 
        textAlign: 'center', 
        marginBottom: '20px',
        padding: '10px',
        backgroundColor: '#2196F3',
        color: 'white',
        borderRadius: '5px'
      }}>
        <h1>SCADA Web Prototype</h1>
        <div style={{ 
          display: 'flex', 
          justifyContent: 'space-between',
          fontSize: '14px',
          marginTop: '10px'
        }}>
          <span>
            Status: 
            <span style={{ 
              color: wsConnected ? '#4CAF50' : '#f44336',
              fontWeight: 'bold',
              marginLeft: '5px'
            }}>
              {wsConnected ? 'Connected' : 'Disconnected'}
            </span>
          </span>
          {lastUpdate && <span>Last update: {lastUpdate}</span>}
        </div>
        {error && (
          <div style={{ color: '#f44336', marginTop: '10px' }}>
            Error: {error}
          </div>
        )}
      </header>
      
      <main>
        <TagList tags={tags} />
      </main>
    </div>
  );
}

export default App;
