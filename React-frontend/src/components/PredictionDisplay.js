import React, { useEffect, useState } from 'react';
import './PredictionDisplay.css';
import { useAuth } from './AuthContext';
import { useNavigate } from 'react-router-dom';

const PredictionDisplay = () => {
    const [predictions, setPredictions] = useState([]);
    const [error, setError] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const [searchQuery, setSearchQuery] = useState(''); 
    const predictionsPerPage = 10;
    const { isAuthenticated } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token'); 
        if (!isAuthenticated || !token) {
            navigate('/login');
        }
    }, [isAuthenticated, navigate]);

    useEffect(() => {
        const fetchPredictions = async () => {
            const token = localStorage.getItem('token'); 
            try {
                const response = await fetch('http://localhost:8080/api/predictions', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`, 
                        'Content-Type': 'application/json'
                    },
                });

                if (!response.ok) {
                    throw new Error('Failed to fetch predictions');
                }

                const data = await response.json();
                setPredictions(data);
            } catch (error) {
                console.error('Error fetching prediction data:', error);
                setError('Failed to fetch prediction data');
            }
        };

        fetchPredictions();
    }, []);

    const handleSearchChange = (event) => {
        setSearchQuery(event.target.value);
    };

    const filteredPredictions = predictions.filter((prediction) =>
        prediction.sport.toLowerCase().includes(searchQuery.toLowerCase()) 
    );

    if (error) {
        return <div className="error-message">{error}</div>;
    }

    if (!predictions.length) {
        return <div>Loading...</div>;
    }

    const indexOfLastPrediction = currentPage * predictionsPerPage;
    const indexOfFirstPrediction = indexOfLastPrediction - predictionsPerPage;
    const currentPredictions = filteredPredictions.slice(indexOfFirstPrediction, indexOfLastPrediction);

    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    return (
        <div className="predictions">
            <h1>All Predictions</h1>
             
            <div className="input">
            <input 
                type="text"
                placeholder="Search by sport (e.g., tennis)"
                value={searchQuery}
                onChange={handleSearchChange}
                className="search-input"
            />
            </div>
            <div className="table-container">
                <div className="table">
                    <div className="table-header">
                        <div>Prediction</div>
                        <div>Sport</div>
                        <div>Bet</div>
                        <div>Author</div>
                        <div>Potential Win</div>
                    </div>
                    {currentPredictions.map((prediction, index) => (
                        <div className="table-row" key={index}>
                            <div>{prediction.predict}</div>
                            <div>{prediction.sport}</div>
                            <div>{prediction.bet}</div>
                            <div>{prediction.author}</div>
                            <div>{prediction.potentialWin}</div>
                        </div>
                    ))}
                </div>
            </div>
            <div className="pagination">
                {Array.from({ length: Math.ceil(filteredPredictions.length / predictionsPerPage) }, (_, index) => (
                    <span
                        key={index}
                        onClick={() => paginate(index + 1)}
                        className={`page-link ${currentPage === index + 1 ? 'active' : ''}`}
                    >
                        {index + 1}
                    </span>
                ))}
            </div>
        </div>
    );
};

export default PredictionDisplay;
