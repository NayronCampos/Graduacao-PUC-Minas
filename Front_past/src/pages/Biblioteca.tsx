
import React, { useState } from 'react';
import { Search, Book, Filter } from 'lucide-react';
import NavBar from '@/components/NavBar';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Card, CardContent } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';

// Sample book data
const books = [
  {
    id: 1,
    title: 'Algebrista Volume 1 Teoria e Exercícios de Fixação',
    subject: 'Matemática',
    publisher: 'Ciência Moderna',
    description: 'Algebrista é todo aquele que possui grande habilidade em álgebra. Para passar nos concursos do colégio naval e atingir o aluno precisa se tornar um algebrista. Para iniciar a preparação para concursos é necessário, primeiramente, o aluno precisa ser um algebrista, dada a complexidade das questões e ao curto tempo para resolver cada questão.',
    link: '#'
  },
  {
    id: 2,
    title: 'Gramática completa para concursos e vestibulares',
    subject: 'Português',
    publisher: 'Saraiva',
    description: 'Um guia completo de gramática portuguesa focado em concursos públicos e vestibulares, contendo teoria e exercícios.',
    link: '#'
  },
  {
    id: 3,
    title: 'Direito Constitucional Esquematizado',
    subject: 'Direito',
    publisher: 'Método',
    description: 'Manual completo de Direito Constitucional com conteúdo organizado em esquemas para facilitar o aprendizado.',
    link: '#'
  },
  {
    id: 4,
    title: 'História do Brasil: da colônia à república',
    subject: 'História',
    publisher: 'Atlas',
    description: 'Livro que aborda de forma concisa e completa a história do Brasil desde o período colonial até a república contemporânea.',
    link: '#'
  }
];

const subjects = ['Todas', 'Matemática', 'Português', 'Direito', 'História', 'Geografia', 'Física', 'Química', 'Biologia'];

const Biblioteca = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedSubject, setSelectedSubject] = useState('Todas');

  const filteredBooks = books.filter(book => {
    const matchesSearch = book.title.toLowerCase().includes(searchTerm.toLowerCase()) || 
                         book.description.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesSubject = selectedSubject === 'Todas' || book.subject === selectedSubject;
    
    return matchesSearch && matchesSubject;
  });

  return (
    <div className="min-h-screen bg-gray-100">
      <NavBar />
      <main className="container mx-auto px-4 pt-24 pb-24">
        <div className="bg-white rounded-lg shadow-md p-6 mb-8">
          <h1 className="text-3xl font-bold text-center text-vagacerta-blue mb-8">Lista de Livros</h1>
          
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Pesquisar:</label>
              <div className="relative">
                <Input 
                  type="text" 
                  placeholder="Digite para pesquisar..." 
                  className="pl-10"
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" size={18} />
              </div>
            </div>
            
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Filtrar por Matéria:</label>
              <Select value={selectedSubject} onValueChange={setSelectedSubject}>
                <SelectTrigger className="w-full">
                  <SelectValue placeholder="Selecione uma matéria" />
                </SelectTrigger>
                <SelectContent>
                  {subjects.map(subject => (
                    <SelectItem key={subject} value={subject}>
                      {subject}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>
          </div>
          
          <div className="space-y-6">
            {filteredBooks.length > 0 ? (
              filteredBooks.map(book => (
                <Card key={book.id} className="overflow-hidden">
                  <CardContent className="p-6">
                    <h2 className="text-xl font-bold text-vagacerta-blue mb-2">{book.title}</h2>
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
                      <div>
                        <p className="text-sm"><span className="font-semibold">Matéria:</span> {book.subject}</p>
                        <p className="text-sm"><span className="font-semibold">Editora:</span> {book.publisher}</p>
                      </div>
                    </div>
                    <p className="text-sm mb-4"><span className="font-semibold">Descrição:</span> {book.description}</p>
                    <div className="flex justify-end">
                      <Button size="sm" variant="link" className="text-blue-600 hover:text-blue-800">
                        Clique Aqui
                      </Button>
                    </div>
                  </CardContent>
                </Card>
              ))
            ) : (
              <div className="text-center py-12">
                <Book className="mx-auto h-12 w-12 text-gray-400 mb-4" />
                <h3 className="text-lg font-medium text-gray-900">Nenhum livro encontrado</h3>
                <p className="mt-1 text-sm text-gray-500">Tente ajustar seus filtros ou termos de pesquisa.</p>
              </div>
            )}
          </div>
        </div>
      </main>

      <footer className="bg-vagacerta-blue text-white py-4 text-center">
        <p>© {new Date().getFullYear()} VagaCerta. Todos os direitos reservados.</p>
        <p className="text-sm mt-1">Contato: vagacerta@gmail.com</p>
      </footer>
    </div>
  );
};

export default Biblioteca;
