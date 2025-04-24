import { author } from "./author.interface";

/**
 * Represents a comment made by a user on a specific post used to display the comment into application
 *
 * @property id – Unique identifier of the comment.  
 * @property content – Text content of the comment.  
 * @property created_at – Timestamp of when the comment was created.  
 * @property author – Information about the comment’s author.  
 * @property post_id – Identifier of the post to which this comment belongs.
 *
 */
export interface Comment {
    id: number;
    content: string;
    created_at: Date;
    author: author;
    post_id: number;
}