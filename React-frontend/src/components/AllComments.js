import React, { useEffect, useState } from 'react';
import { useAuth } from './AuthContext'; 
import { useNavigate } from 'react-router-dom';
import './AllComments.css';

const Comments = () => {
    const [comments, setComments] = useState([]);
    const [error, setError] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const commentsPerPage = 10;
    const { isAuthenticated } = useAuth();
    const navigate = useNavigate();

   
    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!isAuthenticated || !token) {
            navigate('/login'); 
        }
    }, [isAuthenticated, navigate]); 

   
    useEffect(() => {
        const fetchComments = async () => {
            const token = localStorage.getItem('token');
            try {
                const response = await fetch('http://localhost:8080/api/allComment', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`, 
                        'Content-Type': 'application/json'
                    },
                });

                if (!response.ok) {
                    throw new Error('Failed to fetch comments');
                }

                const data = await response.json();
                setComments(data);
            } catch (error) {
                console.error('Error fetching comment data:', error);
                setError('Failed to fetch comment data');
            }
        };

        fetchComments();
    }, []); 

    if (error) {
        return <div className="error-message">{error}</div>; 
    }

    if (!comments.length) {
        return <div>Loading...</div>; 
    }

    const indexOfLastComment = currentPage * commentsPerPage;
    const indexOfFirstComment = indexOfLastComment - commentsPerPage;
    const currentComments = comments.slice(indexOfFirstComment, indexOfLastComment);
   
    const paginate = (pageNumber) => setCurrentPage(pageNumber);

    return (
        <div className="comments">
            <h1>All Comments</h1>
            <div className="table-container">
                <div className="table">
                    <div className="table-header">
                        <div>Author</div>
                        <div>Comment</div>
                    
                    </div>
                    {currentComments.map((comment, index) => (
                        <div className="table-row" key={index}>
                          
                            <div>{comment.author}</div>
                            <div>{comment.textContent}</div>
                          
                        </div>
                    ))}
                </div>
            </div>
            <div className="pagination">
                {Array.from({ length: Math.ceil(comments.length / commentsPerPage) }, (_, index) => (
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

export default Comments;
