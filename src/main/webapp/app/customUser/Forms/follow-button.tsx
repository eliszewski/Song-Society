import React, { useState } from 'react';
import axios from 'axios';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Form, FormGroup, Label, Input } from 'reactstrap';
import { IUser } from 'app/shared/model/user.model';
import 'app/entities/post/post-buttons.scss';
import { IFollow } from '../../shared/model/follow.model';

interface FollowButtonProps {
  userToFollow: IUser;
  currentUser: IUser;
}

const ReplyButton: React.FC<FollowButtonProps> = ({ userToFollow, currentUser }) => {
  const [showModal, setShowModal] = useState(false);
  const [content, setContent] = useState('');

  const handleSubmit = async (event: any) => {
    event.preventDefault();
    const follow: IFollow = {
      date: new Date().toISOString(),
      follower: currentUser,
      followed: userToFollow,
    };

    try {
      await axios.post('/api/follows', follow);
      setContent('');
      setShowModal(false);
    } catch (error) {
      console.error(error);
    }
  };
  return (
    <>
      <Button className="reply-button" onClick={() => setShowModal(true)}>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="16"
          height="16"
          fill="currentColor"
          className="bi bi-arrow-return-right"
          viewBox="0 0 16 16"
        >
          <path
            fill-rule="evenodd"
            d="M1.5 1.5A.5.5 0 0 0 1 2v4.8a2.5 2.5 0 0 0 2.5 2.5h9.793l-3.347 3.346a.5.5 0 0 0 .708.708l4.2-4.2a.5.5 0 0 0 0-.708l-4-4a.5.5 0 0 0-.708.708L13.293 8.3H3.5A1.5 1.5 0 0 1 2 6.8V2a.5.5 0 0 0-.5-.5z"
          />
        </svg>{' '}
        <span className="d-none d-md-inline">{'Reply'}</span>
      </Button>
      <Modal isOpen={showModal} toggle={() => setShowModal(false)}>
        <ModalHeader toggle={() => setShowModal(false)}>Reply</ModalHeader>
        <ModalBody>
          <Form onSubmit={handleSubmit}>
            <FormGroup>
              <Label for="content">Content</Label>
              <Input type="textarea" name="content" id="content" value={content} onChange={e => setContent(e.target.value)} />
            </FormGroup>
            <Button type="submit">Submit</Button>
          </Form>
        </ModalBody>
      </Modal>
    </>
  );
};

export default ReplyButton;
