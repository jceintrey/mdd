/**
 * Request payload for user login.
 *
 * @remarks
 * This interface defines the shape of the data sent to the backend when
 * logging in.
 *
 * @property identifier – can be username or user email
 * @property password - clear password
 *
 */
export interface LoginRequest {
  identifier: string;
  password: string;
}
