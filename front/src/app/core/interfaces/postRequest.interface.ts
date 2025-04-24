/**
 * Request payload for post creation
 *
 * @remarks
 * This interface defines the shape of the data sent to the backend when
 * creating a new post
 *
 * @property topicId – identifier of the related topic
 * @property title - title of the post
 * @property content - content of the post
 *
 */
export interface PostRequest {
    topicId: number;
    title: string;
    content: string;
}