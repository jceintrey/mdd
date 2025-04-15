

export interface User {
    id: number;
    username: string;
    email: string;
}

export interface UserUpdateRequest {
    username: string;
    email: string;
    password?: string;
}