import { author } from "./author.interface";
import { Comment } from "./comment.interface";
import { Topic } from "./topic.interface";

/**
 * Represents a post entity with optional full details.
 *
 * The `id` and `comments` fields are optional to accommodate summary vs. detailed payloads.
 *
 * @property id? – Unique identifier of the post (undefined in creation requests).
 * @property title – Title of the post.
 * @property created_at – ISO timestamp string when the post was created.
 * @property author – The post’s author details.
 * @property topic – The topic to which this post belongs.
 * @property content? – Full content body of the post (absent in summary responses).
 * @property comments? – Array of associated comments when fetching detailed post info.
 *
 */
export interface Post {
    id?: number;
    title: string;
    created_at: string;
    author: author;
    topic: Topic;
    content?: string;
    comments?: Comment[]
}
/**
 * Response shape for paginated post queries.
 *
 * @remarks
 * Mirrors the backend’s pageable response, including content and pagination metadata.
 *
 * @property content – Array of `Post` items for the current page.
 * @property pageable – Paging parameters (e.g., page number, size, sort).
 * @property totalElements – Total number of posts across all pages.
 * @property totalPages – Total number of available pages.
 *
 */
export interface PostResponse {
    content: Post[];
    pageable: any;
    totalElements: number;
    totalPages: number;
}