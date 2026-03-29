import React, { useState } from 'react';
import { useAuth } from '../AuthContext';

const Register = ({ onSwitch }) => {
    const [name, setName] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [dailySafeLimit, setDailySafeLimit] = useState(120);
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');
    const { register } = useAuth();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setMessage('');
        const res = await register(username, password, name, parseInt(dailySafeLimit));
        if (res.success) {
            setMessage('Registration successful! Redirecting to login...');
            setTimeout(() => onSwitch(), 1500);
        } else {
            setError(res.message);
        }
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh', padding: '1.5rem' }}>
            <div className="glass-panel" style={{ width: '100%', maxWidth: '450px', textAlign: 'center' }}>
                <h1 style={{ marginBottom: '1.5rem', color: 'var(--accent-color)' }}>Join Digital Detox</h1>
                <p style={{ color: 'var(--text-secondary)', marginBottom: '2rem' }}>Experience a healthier digital life</p>
                
                {error && <div style={{ color: 'var(--danger)', marginBottom: '1rem', background: 'rgba(255, 68, 68, 0.1)', padding: '0.8rem', borderRadius: '8px' }}>{error}</div>}
                {message && <div style={{ color: 'var(--success)', marginBottom: '1rem', background: 'rgba(0, 255, 136, 0.1)', padding: '0.8rem', borderRadius: '8px' }}>{message}</div>}
                
                <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1.2rem' }}>
                    <div style={{ textAlign: 'left' }}>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', color: 'var(--text-secondary)' }}>Full Name</label>
                        <input 
                            type="text" 
                            placeholder="John Doe"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                        />
                    </div>
                    <div style={{ textAlign: 'left' }}>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', color: 'var(--text-secondary)' }}>Username</label>
                        <input 
                            type="text" 
                            placeholder="johndoe123"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div style={{ textAlign: 'left' }}>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', color: 'var(--text-secondary)' }}>Password</label>
                        <input 
                            type="password" 
                            placeholder="••••••••"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <div style={{ textAlign: 'left' }}>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.9rem', color: 'var(--text-secondary)' }}>Daily Safe Limit (min)</label>
                        <input 
                            type="number" 
                            value={dailySafeLimit}
                            onChange={(e) => setDailySafeLimit(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" style={{ marginTop: '1rem' }}>Register</button>
                </form>
                
                <p style={{ marginTop: '2rem', color: 'var(--text-secondary)', fontSize: '0.9rem' }}>
                    Already have an account? <span onClick={onSwitch} style={{ color: 'var(--accent-color)', cursor: 'pointer', fontWeight: '600' }}>Login here</span>
                </p>
            </div>
        </div>
    );
};

export default Register;
