/**
 * Request payload for creating a new comment.
 *
 * @remarks
 * This interface defines the shape of the data sent to the backend when
 * adding a comment to a post. It only includes the `content` field since
 * the post identifier and author are determined by the route and authentication context.
 *
 * @property content – The text content of the comment. Must be a non‑empty string.
 *
 */
export interface CommentRequest {
    "content": string
}