import React from 'react';
function Home() {
    return (
        <div style={{ textAlign: 'center', marginTop: '50px' }}>
            <h2>Welcome to Our App</h2>
            <p>Please choose an option:</p>
            <div>
                <button 
                    style={{ margin: '10px', padding: '10px 20px' }}
                    onClick={() => window.location.href = '/login'}
                >
                    Login
                </button>

                <button 
                    style={{ margin: '10px', padding: '10px 20px' }}
                    onClick={() => window.location.href = '/signup'}
                >
                    Sign Up
                </button>
            </div>
        </div>
    );
}

export default Home;
