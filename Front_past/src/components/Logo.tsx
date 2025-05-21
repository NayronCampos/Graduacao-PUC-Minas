import React from 'react';
import { cn } from '@/lib/utils';
interface LogoProps {
  className?: string;
}
const Logo: React.FC<LogoProps> = ({
  className
}) => {
  return <div className={cn("flex items-center", className)}>
      <h1 className="text-2xl font-bold tracking-tight">
        <span style={{
        color: "#1e4b7a"
      }}>VAGA</span>
        <span className="text-yellow-500 ml-0 mx-0">CERTA</span>
      </h1>
    </div>;
};
export default Logo;