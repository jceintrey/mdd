import { author } from "./author.interface";

export interface Comment {
    id: number;
    content: string;
    created_at: Date;
    author: author;
    post_id: number;
}