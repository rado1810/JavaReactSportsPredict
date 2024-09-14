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

    // Проверка за валиден токен и сесия
    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!isAuthenticated || !token) {
            navigate('/login'); 
        }
    }, [isAuthenticated, navigate]); 

    // Извличане на коментарите
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
    
    // Функция за изтриване на коментар
    const deleteComment = async (id) => {
        const token = localStorage.getItem('token');
        console.log(id)
        
        try {
            const response = await fetch(`http://localhost:8080/api/allComment${id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });
            
            if (!response.ok) {
                throw new Error('Failed to delete comment');
            }

            // Обновяване на списъка с коментари след успешно изтриване
            setComments(comments.filter((comment) => comment.id !== id));
        } catch (error) {
            console.error('Error deleting comment:', error);
            setError('Failed to delete comment');
        }
    };
    
    // Показване на съобщение за грешка
    if (error) {
        return <div className="error-message">{error}</div>; 
    }

    // Зареждане на коментарите
    if (!comments.length) {
        return <div>Loading...</div>; 
    }

    // Логика за пагинация
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
                        <div>Actions</div>
                    </div>
                    {currentComments.map((comment) => (
                        
                        <div className="table-row" key={comment.id} >
                            
                            <div>{comment.author}</div>
                            <div>{comment.textContent}</div>
                            <div>
                            
                                <button 
                                    className="delete-button" 
                                    onClick={() => deleteComment(comment.id)}
                                >
                                    Delete
                                </button>
                            </div>
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
